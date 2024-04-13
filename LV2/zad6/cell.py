import ast

class Cell:
    def __init__(self, sheet, exp, name) -> None:
        self.sheet = sheet
        self.name = name
        self.listeners = []
        self.set_exp(exp)

    def get_exp(self):
        return self.exp

    def get_name(self):
        return self.name

    def set_exp(self, exp):
        # print("Set content for " + self.name + " to " + exp)
        oldrefs = self.getrefs()

        if hasattr(self, "exp"):
            for expr in self.get_refs_from_text(exp):
                if self.sheet.get_table()[expr] in self.listeners or self == self.sheet.get_table()[expr]:
                    raise RuntimeError("Circular dependency")

        self.exp = exp
        self.value = self.evaluate(exp, self.sheet.get_table())

        refs = self.getrefs()

        for expr in refs:
            self.sheet.get_table()[expr].add_listener(self)
            # print("Adding new listener to " + self.sheet.get_table()[expr].get_name() + " which is " + self.get_name())

        # print("Refs for " + self.get_name() + " are " + str(self.getrefs()))

        for oldref in oldrefs:
            if oldref not in refs:
                # print("Removing outdated listener " + oldref + " from " + self.sheet.get_table()[oldref].get_name())
                self.sheet.get_table()[oldref].remove_listener(self)

        self.notify_listeners()

    def getrefs(self):
        refs = []

        def visit(node):
            if isinstance(node, ast.Name):
                refs.append(node.id)
            elif isinstance(node, ast.BinOp):
                visit(node.left)
                visit(node.right)

        if not hasattr(self, "exp"):
            return []

        tree = ast.parse(str(self.exp), mode='eval')

        visit(tree.body)

        return refs
    
    def get_refs_from_text(self, text):
        refs = []

        def visit(node):
            if isinstance(node, ast.Name):
                # print("NID")
                # print(node.id)
                # print("EXP")
                # print(self.sheet.get_table()[node.id].get_exp())
                newrefs = self.sheet.get_table()[node.id].get_refs_from_text(self.sheet.get_table()[node.id].get_exp())
                # print(newrefs)

                if self.name in newrefs:
                    raise RuntimeError("Circular dependency")
                
                refs.append(self.sheet.get_table()[node.id].get_name())
                refs.extend(newrefs)
            elif isinstance(node, ast.BinOp):
                visit(node.left)
                visit(node.right)

        tree = ast.parse(text, mode='eval')

        visit(tree.body)

        return refs

    def evaluate(self, exp, variables={}):
        def _eval(node):
            if isinstance(node, ast.Constant):
                return node.n
            elif isinstance(node, ast.Name):
                return variables[node.id]
            elif isinstance(node, ast.BinOp):
                return _eval(node.left) + _eval(node.right)
            else:
                raise Exception('Unsupported type {}'.format(node))

        node = ast.parse(exp, mode='eval')
        return _eval(node.body)
    
    def add_listener(self, listener):
        self.listeners.append(listener)

    def remove_listener(self, listener):
        self.listeners.remove(listener)

    def notify_listeners(self):
        for listener in self.listeners:
            listener.notify()

    def notify(self):
        self.value = self.evaluate(self.exp, self.sheet.get_table())
        self.notify_listeners()

    def __str__(self):
        return str(self.exp) + " = " + str(self.value)
    
    def __add__(self, o):
        return self.value + o.value