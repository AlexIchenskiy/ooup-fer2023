from sheet import Sheet

if __name__ == "__main__":
    s=Sheet(5,5)
    print()

    s.set('A1','2')
    s.set('A2','5')
    s.set('A3','A1+A2')
    s.print()
    print()

    s.set('A1','4')
    s.set('A4','A1+A3')
    s.print()
    print()

    try:
        s.set('A1','A3')
    except RuntimeError as e:
        print("Caught exception:", e)
    s.print()
    print()

    try:
        s.set('A4','A3+A4')
    except RuntimeError as e:
        print("Caught exception:", e)
    s.print()
    print()

    s.set('A1', '8')
    s.print()
    print()

    # Test circular dependency with depth 2
    try:
        s.set('A2', 'A4')
    except RuntimeError as e:
        print("Caught exception:", e)
    s.print()
    print()

    # Test circular dependency with depth 3
    s.set('C1', '2')
    s.set('C2', 'C1')
    s.set('C3', 'C2')
    s.set('C4', 'C3')

    try:
        s.set('C1', 'C4')
    except RuntimeError as e:
        print("Caught exception:", e)
    s.print()
    print()

    # Test more dynamic changes
    s.set('B1', '5')
    s.set('B2', '7')
    s.set('B3', '9')
    s.set('B4', 'B1+B2')
    s.set('B5', 'B2+B3')
    s.print()
    print()

    s.set('B1', '15')
    s.print()
    print()

    s.set('B3', '19')
    s.print()
    print()

    s.set('B2', '17')
    s.print()
    print()