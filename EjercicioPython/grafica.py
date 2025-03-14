import pandas as pd
import matplotlib.pyplot as plt
print(pd.__version__)

df = pd.read_csv('EjercicioPython\housing.csv')

#mostrar las primeras 5 filas
#print(df.head())

#Las ultimas 5 filas
#print(df.tail())

#quiero una fila en especifico
#print(df.iloc[7])

#mostrar una columna por su nombre
#print(df['ocean_proximity'])

#obtener la media de la columna de total de cuartos
mediacuartos =  df['total_rooms'].mean()
print('Media de los cuartos: ', mediacuartos)

inicio = int(input(""" Decida la estadística a calcular.
        1. Longitud
        2. Latitud
        3. Edad media de la vivienda
        4. Total de habitaciones
        5. Total de dormitorios
        6. Población
        7. Viviendas
        8. Promedio de ingresos
        9. Valor promedio de la casa
        """))

columna = ['longitude', 'latitude', 'housing_median_age', 'total_rooms', 'total_bedrooms', 'population',
           'households', 'median_income','median_house_value','ocean_proximity']
    

#obtener la mediana 
mediana = df[columna[inicio-1]].median()
media = df[columna[inicio-1]].mean()
moda = df[columna[inicio-1]].mode()[0]
rango = df[columna[inicio-1]].max() - df[columna[inicio-1]].min()
varianza = df[columna[inicio-1]].var()
desviacion_estandar = df[columna[inicio-1]].std()

print(f"Media: {media}")
print(f"Mediana: {mediana}")
print(f"Moda: {moda}")
print(f"Rango: {rango}")
print(f"Varianza: {varianza}")
print(f"Desviación Estándar: {desviacion_estandar}")

tabla_frecuencias = df[columna[inicio-1]].value_counts().reset_index()
tabla_frecuencias.columns = ['Valor', 'Frecuencia']
tabla_frecuencias = tabla_frecuencias.sort_values(by='Valor')

print(tabla_frecuencias)

# Agrupar precios de casas por rangos y contar la población
df['price_bins'] = pd.cut(df['median_house_value'], bins=10)
tabla_frecuencias = df.groupby('price_bins')['population'].sum().reset_index()

# Extraer las categorías (rangos de precios) y las frecuencias (población total por rango)
categorias = tabla_frecuencias['price_bins'].astype(str)
frecuencias = tabla_frecuencias['population']

# Graficar
plt.figure(figsize=(9, 5))
plt.bar(categorias, frecuencias, color='#0000ff')

# Títulos y etiquetas
plt.title('Población y Costo Promedio de la Casa')
plt.xlabel('Rango de Precio de la casa')
plt.ylabel('Población')



# Mostrar gráfico
plt.xticks(rotation=45)
plt.grid(axis='y', linestyle='--', alpha=0.7)
plt.show()