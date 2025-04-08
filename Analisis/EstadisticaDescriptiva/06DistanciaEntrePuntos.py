import numpy as np
import pandas as pd
from scipy.spatial import distance

#definimos las coordenadas de nuestro sistema de tiendas

tiendas = {
    "Tienda A": (1,1),
    "Tienda B": (1,5),
    "Tienda C": (7,1),
    "Tienda D": (3,3),
    "Tienda E": (4,8),
    }

df_tiendas = pd.DataFrame(tiendas).T
df_tiendas.columns= ['x', 'y']
print ('coordenadas de las tiendas: ')
print(df_tiendas)

distancias_punto1 = pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)
distancias_punto2 = pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)
distancias_punto3 = pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)

#vamos a calcular las distancias
for i in df_tiendas.index:
    for j in df_tiendas.index:
        #defino la distancia eucidriana del primer punto
        distancias_punto1.loc[i,j] = distance.euclidean(df_tiendas.loc[i], df_tiendas.loc[j])
        distancias_punto2.loc[i,j] = distance.cityblock(df_tiendas.loc[i], df_tiendas.loc[j])
        distancias_punto3.loc[i,j] = distance.chebyshev(df_tiendas.loc[i], df_tiendas.loc[j])
        
#mostrar resultados
print('/n Distancia Euclidiana entre cada una de las tiendas: ')
print(distancias_punto1)
print('/n Distancia Manhattan entre cada una de las tiendas: ')
print(distancias_punto2)
print('/n Distancia Chebyshev entre cada una de las tiendas: ')
print(distancias_punto3)

#Calcularemos las distancias entre todos los pares de puntos y determinaremos cuáles están más alejados entre sí y cuáles están más cercanos, utilizando las distancias Euclidiana, Manhattan y Chebyshev.
#Ejercicio: Determinación de Distancias entre Puntos
#Puntos en el Plano

#Los puntos en el plano son los siguientes:

#    Punto A: (2, 3)
#   Punto B: (5, 4)
#    Punto C: (1, 1)
#    Punto D: (6, 7)
#    Punto E: (3, 5)
#    Punto F: (8, 2)
#    Punto G: (4, 6)
#    Punto H: (2, 1)

# Definimos las coordenadas de los puntos
puntos = {
    "Punto A": (2, 3),
    "Punto B": (5, 4),
    "Punto C": (1, 1),
    "Punto D": (6, 7),
    "Punto E": (3, 5),
    "Punto F": (8, 2),
    "Punto G": (4, 6),
    "Punto H": (2, 1),
}

df_puntos = pd.DataFrame(puntos).T
df_puntos.columns = ['x', 'y']
print('Coordenadas de los puntos:')
print(df_puntos)

# Creamos las matrices vacías para las distancias
distancia_euclidiana = pd.DataFrame(index=df_puntos.index, columns=df_puntos.index)
distancia_manhattan = pd.DataFrame(index=df_puntos.index, columns=df_puntos.index)
distancia_chebyshev = pd.DataFrame(index=df_puntos.index, columns=df_puntos.index)

# Calculamos las distancias
for i in df_puntos.index:
    for j in df_puntos.index:
        distancia_euclidiana.loc[i, j] = distance.euclidean(df_puntos.loc[i], df_puntos.loc[j])
        distancia_manhattan.loc[i, j] = distance.cityblock(df_puntos.loc[i], df_puntos.loc[j])
        distancia_chebyshev.loc[i, j] = distance.chebyshev(df_puntos.loc[i], df_puntos.loc[j])

# Mostrar resultados
print('\nDistancia Euclidiana entre cada uno de los puntos:')
print(distancia_euclidiana)

print('\nDistancia Manhattan entre cada uno de los puntos:')
print(distancia_manhattan)

print('\nDistancia Chebyshev entre cada uno de los puntos:')
print(distancia_chebyshev)
