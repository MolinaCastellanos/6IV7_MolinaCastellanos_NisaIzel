
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
import numpy as np

ruta_df2 = "6IV7_MolinaCastellanos_NisaIzel\Analisis\Catalogo_sucursal.xlsx"
ruta_df = "6IV7_MolinaCastellanos_NisaIzel\Analisis\proyecto1.xlsx"

df2 = pd.read_excel(ruta_df2)
df = pd.read_excel(ruta_df)

#1 
# Conocer las ventas totales del comercio

columna = 'ventas_tot'  
columna2 = 'suc'         
columna_id = 'id_sucursal'  

for id in df2[columna_id].unique():
    ventas = df[df[columna_id] == id][columna].sum()
    
    sucursal_info = df2[df2[columna_id] == id][columna2].values
    sucursal = sucursal_info[0] if len(sucursal_info) > 0 else 'Desconocida'

    print(f'Ventas totales de {sucursal}: {ventas}\n')

#2
# Conocer cuantos socios tienen adeudo y cuantos no tienen adeudo con su porcentaje correspondiente

#inicializar variables para contar los que tienen adeudos y los que no
con=0
sin=0

#hacer for para contar cuando tienen adeudos 
for adeudo in df['B_adeudo']:
    if adeudo == 'Con adeudo':
        con +=1
    else:
        sin+=1

socios= con +sin
porcon=(con*100)/socios
porsin=(sin*100)/socios

print('socios con adeudo: ',con,', ',porcon,'%','\n')
print('socios sin adeudo: ',sin,', ',porsin,'%','\n')

#3
# Grfica de barras de ventas totales a lo largo del tiempo 

# Convertir la columna de tiempo a formato datetime
df['fec_ini_cdto'] = pd.to_datetime(df['fec_ini_cdto'])

# Ordenar los datos por fecha (opcional, pero recomendable)
df = df.sort_values(by='fec_ini_cdto')

ventas_totales = df['ventas_tot']
tiempo = df['fec_ini_cdto']

# Configurar la figura
plt.figure(figsize=(12, 6))
plt.bar(tiempo, ventas_totales, color='#43d53a')

# Formato de fecha en el eje X
plt.gca().xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d'))  # Formato Año-Mes-Día
plt.gca().xaxis.set_major_locator(mdates.AutoDateLocator())  # Espaciado automático de fechas

plt.title('Ventas totales al pasar el tiempo')
plt.xlabel('Tiempo')
plt.ylabel('Ventas totales')
plt.xticks(rotation=45)
plt.grid(axis='y', linestyle='--', alpha=0.7)

plt.show()

#4
# Grafica donde se pueda visualizar la desviación estándar 
# de los pagos realizados del comercio respecto del tiempo

# Convertir la columna B_mes a formato datetime
df['B_mes'] = pd.to_datetime(df['B_mes'], format='%Y-%m')  # Asegurar formato YYYY-MM

# Hacer merge para obtener el nombre de la sucursal
df_merged = df.merge(df2, on='id_sucursal', how='left')

# Agrupar por mes y sucursal, calculando la desviación estándar de pagos
df_std = df_merged.groupby(['B_mes', 'suc'])['pagos_tot'].std().reset_index()

# Lista de colores para cada sucursal única
colores = plt.cm.tab10(np.linspace(0, 1, df_std['suc'].nunique()))

# Crear la gráfica
plt.figure(figsize=(12, 6))

# Graficar cada sucursal con un color diferente
for i, (sucursal, grupo) in enumerate(df_std.groupby('suc')):
    plt.bar(grupo['B_mes'], grupo['pagos_tot'], color=colores[i], label=f'Sucursal: {sucursal}', alpha=0.7, width=20)

# Formatear el eje X con fechas reales
plt.gca().xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m'))
plt.gca().xaxis.set_major_locator(mdates.MonthLocator())

# Personalización de la gráfica
plt.title('Desviación Estándar de los Pagos Realizados por Sucursal en el Tiempo')
plt.xlabel('Mes')
plt.ylabel('Desviación Estándar de Pagos Totales')
plt.xticks(rotation=45)
plt.legend(title='Sucursal')
plt.grid(axis='y', linestyle='--', alpha=0.7)

# Mostrar la gráfica
plt.show()

#5
#Cuanto es la deuda total de los clientes

deuda1='adeudo_actual'

for id in df2[columna_id].unique():
    deudas = df[df[columna_id] == id][deuda1].sum()
    
    sucursal_info = df2[df2[columna_id] == id][columna2].values
    sucursal = sucursal_info[0] if len(sucursal_info) > 0 else 'Desconocida'

    print(f'Deuda total de los clientes en {sucursal}: ${deudas}\n')

#6
#Cuanto es el porcentaje de utilidad del comercio a partir de el total de las ventas respecto del adeudo 

for id in df2[columna_id].unique():
    deudas = df[df[columna_id] == id][deuda1].sum()
    ventas = df[df[columna_id] == id][columna].sum()

    porUtilidad =((ventas-deudas)/ventas)*100
    
    sucursal_info = df2[df2[columna_id] == id][columna2].values
    sucursal = sucursal_info[0] if len(sucursal_info) > 0 else 'Desconocida'

    print(f'Porcentaje de utilidad de {sucursal}: {porUtilidad}%\n')

#7
# Crear un grafico circular de ventas por sucursal.

# Sumar las ventas por sucursal
ventas_por_sucursal = df_merged.groupby('suc')['ventas_tot'].sum()

# Crear la figura
plt.figure(figsize=(8, 8))

# Generar el gráfico de pastel
plt.pie(
    ventas_por_sucursal, 
    labels=ventas_por_sucursal.index, 
    autopct='%1.1f%%', 
    colors=plt.cm.Paired.colors, 
    startangle=140
)

# Agregar título
plt.title('Distribución de Ventas Totales por Sucursal')

# Mostrar el gráfico
plt.show()

#8
# Presentar un grafico de cuales son las deudas totales por cada sucursal
# respecto del margen de utilidad de cada sucursal.

# Calcular la deuda total y el margen de utilidad por sucursal
deuda_por_sucursal = df_merged.groupby('suc')['adeudo_actual'].sum()
ventas_por_sucursal = df_merged.groupby('suc')['ventas_tot'].sum()
utilidad_por_sucursal = ((ventas_por_sucursal - deuda_por_sucursal) / ventas_por_sucursal) * 100

# Crear el gráfico de barras
fig, ax1 = plt.subplots(figsize=(12, 6))

# Gráfico de deudas totales
color = 'tab:red'
ax1.set_xlabel('Sucursal')
ax1.set_ylabel('Deuda Total ($)', color=color)
ax1.bar(deuda_por_sucursal.index, deuda_por_sucursal, color=color, alpha=0.7, label='Deuda Total')
ax1.tick_params(axis='y', labelcolor=color)

# Crear un segundo eje Y para el margen de utilidad
ax2 = ax1.twinx()
color = 'tab:blue'
ax2.set_ylabel('Margen de Utilidad (%)', color=color)
ax2.plot(utilidad_por_sucursal.index, utilidad_por_sucursal, color=color, marker='o', linestyle='-', linewidth=2, label='Margen de Utilidad')
ax2.tick_params(axis='y', labelcolor=color)

# Configurar el gráfico
plt.title('Deuda Total vs. Margen de Utilidad por Sucursal')
fig.tight_layout()

# Agregar leyendas
ax1.legend(loc='upper left')
ax2.legend(loc='upper right')

# Mostrar el gráfico
plt.show()