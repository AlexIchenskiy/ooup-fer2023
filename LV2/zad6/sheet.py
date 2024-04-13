import ast

from cell import Cell

class Sheet:
    def __init__(self, x, y) -> None:
        self.x = x
        self.y = y
        self.table: dict[str, Cell] = {}

    def get_table(self):
        return self.table

    def cell(self, ref) -> Cell:
        return self.table[ref]
    
    def set(self, ref, content) -> None:
        if len(ref) != 2 or ord(ref[0]) < 65 or ord(ref[0]) > 90 or int(ref[1::]) > self.x or int(ref[1::]) > self.y:
            raise Exception("Invalid cell name")
        
        if self.table.get(ref, None) == None:
            self.table[ref] = Cell(self, content, ref)
        else:
            self.table[ref].set_exp(content)

    def getrefs(self, cell):
        temp = self.table.get(cell, None)
        if temp == None:
            return []
        return temp.getrefs()

    def evaluate(self, cell):
        temp = self.table.get(cell, None)
        if temp == None:
            return []
        return temp.evaluate(temp.exp)

    def print(self):
        max = 0
        for i in range(0, self.x):
            for j in range(0, self.y):
                cellname = chr(65 + i) + str(j + 1)
                celllen = len(str(self.table.get(cellname, "undefined"))) + len(cellname) + 6
                if celllen > max:
                    max = celllen

        for i in range(0, self.x):
            for j in range(0, self.y):
                cellname = chr(65 + i) + str(j + 1)
                out = cellname + " = " + str(self.table.get(cellname, "undefined"))
                print(out.ljust(max), end=" ")
            print()
        pass