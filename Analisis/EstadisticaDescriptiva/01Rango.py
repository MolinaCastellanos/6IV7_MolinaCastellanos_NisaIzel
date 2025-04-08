#vamos a crear un programa que pregunte al usuario por las ventas de un rangoo de años y muestre por pantalla una serie con los datos de las ventas indexadas por los años antes y depsyes de aplicar un decuento del 10%

import pandas as pd
inicio =int(input('Introduce el año inicial de ventas'))
fin=int(input('Introduce el año finañ de ventas'))
ventas = {}
for i in range(inicio, fin+1):
    ventas[i] = float(input('Introduce las ventas del año '+ str(i) + ': '))

ventas = pd.Series(ventas)
print('Ventas \n', ventas)
print('Ventas con descuento\n', ventas*0.9)
