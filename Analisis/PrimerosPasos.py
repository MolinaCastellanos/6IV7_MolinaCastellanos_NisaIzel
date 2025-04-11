import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
import numpy as np

# Carga los dos archivos
df_catalogo = pd.read_excel("D:\\Ciberseguridad\\6IV7_MolinaCastellanos_NisaIzel\\Analisis\\Catalogo_sucursal.xlsx")
df_datos = pd.read_excel("D:\\Ciberseguridad\\6IV7_MolinaCastellanos_NisaIzel\\Analisis\\proyecto1.xlsx")

# Une los datos usando 'id_sucursal' como clave
df_resultado = pd.merge(df_datos, df_catalogo, on='id_sucursal', how='left')

df_resultado = df_resultado.drop(columns=['id_sucursal'])

# Guarda el nuevo Excel
df_resultado.to_excel("D:\\Ciberseguridad\\6IV7_MolinaCastellanos_NisaIzel\\Analisis\\proyecto1_1.xlsx", index=False)

df = pd.read_excel("D:\\Ciberseguridad\\6IV7_MolinaCastellanos_NisaIzel\\Analisis\\proyecto1_1.xlsx")

# 1 
# Conocer las ventas totales del comercio
ventas_por_sucursal = df.groupby("suc")["ventas_tot"].sum()
print('Ventas totales:')
print(ventas_por_sucursal)
print()

# 2
# Conocer cuantos socios tienen adeudo y cuantos no tienen adeudo con su porcentaje correspondiente
adeudos_total = df.groupby("B_adeudo")["no_clientes"].sum()
adeudos_mostrar = adeudos_total.reset_index(name="Total")

adeudos_mostrar['Porcentaje'] = (adeudos_mostrar['Total'] / adeudos_mostrar['Total'].sum()) * 100
adeudos_mostrar['Porcentaje'] = adeudos_mostrar['Porcentaje'].round(2)
print(adeudos_mostrar)
print()

#3
# Gráfica de barras de ventas totales a lo largo del tiempo 
df['fec_ini_cdto'] = pd.to_datetime(df['fec_ini_cdto'])
df = df.sort_values(by='fec_ini_cdto')
df['mes'] = df['fec_ini_cdto'].dt.to_period('M')

ventas_totales = df.groupby('mes')['ventas_tot'].sum()
ventas_totales.index = ventas_totales.index.astype(str)

plt.figure(figsize=(10, 6))
plt.bar(ventas_totales.index, ventas_totales.values)

plt.title('Ventas totales por mes')
plt.xlabel('Mes')
plt.ylabel('Ventas totales')
plt.xticks(rotation=45)
plt.grid(axis='y', linestyle='--', alpha=0.7)
#plt.show()

# 5
# Cuanto es la deuda total de los clientes
deuda_total_por_sucursal = df.groupby("suc")["adeudo_actual"].sum()
deuda_total_por_sucursal_mostrar = deuda_total_por_sucursal.reset_index(name="Total de adeudos")
print (deuda_total_por_sucursal_mostrar)
print()

# 6
# Cuanto es el porcentaje de utilidad del comercio a partir de el total de las ventas respecto del adeudo 
resumen = pd.DataFrame({
    "ventas": ventas_por_sucursal,
    "deudas": deuda_total_por_sucursal
})

resumen["porcentaje_utilidad"] = ((resumen["ventas"] - resumen["deudas"]) / resumen["ventas"]) * 100
print (resumen)
print()
 
#7
# Crear un grafico circular de ventas por sucursal.

plt.figure(figsize=(8, 8))
plt.pie(
    ventas_por_sucursal, 
    labels=ventas_por_sucursal.index, 
    autopct='%1.1f%%', 
    colors=plt.cm.Paired.colors, 
    startangle=140
)
plt.title('Distribución de Ventas Totales por Sucursal')
#plt.show() 

# 8
# Presentar un grafico de cuales son las deudas totales por cada sucursal
# respecto del margen de utilidad de cada sucursal.

fig, ax1 = plt.subplots(figsize=(12, 6))

color = 'tab:red'
ax1.set_xlabel('Sucursal')
ax1.set_ylabel('Deuda Total ($)', color=color)
ax1.bar(deuda_total_por_sucursal.index, deuda_total_por_sucursal, color=color, alpha=0.7, label='Deuda Total')
ax1.tick_params(axis='y', labelcolor=color)
ax2 = ax1.twinx()

color = 'tab:blue'
ax2.set_ylabel('Margen de Utilidad (%)', color=color)
ax2.plot(resumen["porcentaje_utilidad"].index, resumen["porcentaje_utilidad"], color=color, marker='o', linestyle='-', linewidth=2, label='Margen de Utilidad')
ax2.tick_params(axis='y', labelcolor=color)
plt.title('Deuda Total vs. Margen de Utilidad por Sucursal')
fig.tight_layout()
ax1.legend(loc='upper left')
ax2.legend(loc='upper right')
plt.show()