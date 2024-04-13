def mymax(iterable, key = lambda x: x):
    # incijaliziraj maksimalni element i maksimalni ključ
    max_x = max_key = None

    # obiđi sve elemente
    for x in iterable:
        # ako je key(x) najveći -> ažuriraj max_x i max_key
        temp = key(x)
        if max_key is None or temp > max_key:
            max_x = x
            max_key = temp

    # vrati rezultat
    return max_x

if __name__ == "__main__":
    intlist = [1, 3, 5, 7, 4, 6, 9, 2, 0]
    string = "Suncana strana ulice"
    stringlist = ["Gle", "malu", "vocku", "poslije", "kise", "Puna", "je", "kapi", "pa", "ih", "njise"]
    D = {'burek':8, 'buhtla':5}
    persons = [("Ime", "Prezime"), ("Mali", "Ivica"), ("Pero", "Peric"), ("Drugi", "Ljudi"), ("Ana", "Anic")]

    maxint = mymax(intlist)
    maxchar = mymax(string)
    maxstring = mymax(stringlist)
    longeststring = mymax(stringlist, lambda x: len(x))
    mostexpensive = mymax(D, D.get)
    lastperson = mymax(persons)

    print("Max of int list [ ", end="")
    for i in intlist:
        print(i, end=" ")
    print("] is: " + str(maxint))

    print("Max of string \"" + string + "\" is: " + str(maxchar), end="")

    print("\nMax of string list [ ", end="")
    for i in stringlist:
        print(i, end=" ")
    print("] is: " + str(maxstring))

    print("Longest of string list [ ", end="")
    for i in stringlist:
        print(i, end=" ")
    print("] is: " + str(longeststring))

    print("The most expensive item of dict [ ", end="")
    for i in D:
        print("(" + i + ", " + str(D.get(i)) + ")", end=" ")
    print("] is: " + str(mostexpensive) + " of price " + str(D.get(str(mostexpensive))))

    print("Last person of list [ ", end="")
    for i in persons:
        print(str(i), end=" ")
    print("] is: " + str(lastperson))