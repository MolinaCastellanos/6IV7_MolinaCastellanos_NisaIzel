#vamos a hacer un ejemplo de carga de archivos y aplicar min, max, media, desviacion estdansdar
import pandas as pd
def cotizacion(fichero):
    df = pd.read.csv(fichero, sep='=', decimal=',', thousands='.', index_col=0)
    return pd.DataFrame([df.min(), df.max(), df.mean(), df.std()], index=['Minimo', 'Maximo', 'Media', 'Desviacion estandar'])

cotizacion('./DIA_1/cotizacion.csv')
