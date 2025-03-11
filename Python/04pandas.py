#
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('./Estadistica_descriptiva/housing.csv')

#Mostrar la primeras 5 filas
print(df.head())

#Las ultimas 5 filas
print(df.tail())

#quiero una fila en especifico
print(df.iloc[7])

#mostrar una columna por su nombre
print(df["ocean_proximity"])

#obtener la media de la columna de total de cuartos
mediacuartos = df["Total_rooms"].mean()
print("Mediana de los cuartos: ", mediacuartos)

#Obtener la media de la columna population
mediapopularidad = df["population"].median()
print("Mediana de popularidad: ", mediapopularidad)

std_age = df["housing_media_age"].std()
print('Desviacion Estandar por años: ', std_age)

#para poder filtrar
filtrodeloceano = df[df["ocean_proximity"] == "ISLAND"]
print('filtro de proximidad del oceano: ', filtrodeloceano)

#vamos a crear un grafico de dispercion entre los registro de proximidad del oceano vs los precios
plt.scatter(df["ocean_proximity"][:10],["median_house_value"][:10])

#hay que definir a x y
plt.xlabel('proximidad')
plt.ylabel('precio')
plt.title('Grafico de dispercion de proximidad al oceano vs precio')
plt.show()