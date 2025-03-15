
const texto = document.getElementById('texto');
const clave = document.getElementById('txtclave');
const btn_Cifrar = document.getElementById('btn_Cifrar');

var vigenere = vigenere || (function () {

    //Aqui tenemos que crear una funcion que se encarge de obtener el texto,
    //desplazamiento y si va a cifrar o descifrar

    var proceso = function (txt, desp, action) {
        var replace = (function(){
            //ABC
            var abc = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
            var longitud = abc.length;

            return function(c) {
                var i = abc.indexOf(c.toLowerCase());
                if (i != -1) {
                    var pos = i;
                    if (action) {
                        //cifrar
                        pos += desp;
                        pos = pos % longitud;
                        console.log('pos', pos);
                    } else {
                        // descifrar ahí lo hacen :3
                        pos -= desp;
                        if (pos < 0) pos += longitud;
                    }
                    return abc[pos];
                }
                return c;
            };
        })();

        //hay que validar la cadena
        var re = (/([a-z])/ig);

        return String(txt).replace (re, function (match) {
            return replace (match);            
        })
    };
    return {
        //vamor a saber si queremos cifrar o descifrar
        encode : function (txt, desp) {
            return proceso (txt, desp, true);
        },
        decode : function (txt, desp) {
            return proceso(txt, desp, false);
        }
    }
})();

//cuando los campos estén vacíos
function Verificar() {
    bandera = false;
    if (texto.value.trim() != "") {
        if (clave.value.trim() != "") {
            bandera = true;
        }
    }
    return bandera;
}
//cuando la clave sea más grande que el texto para cifrar

//cuando la clave sea más grande que el texto para descifrar
console.log("sdkdsj");

//cifrar
function cifrar(){
    if (!Verificar()) {
        alert ("Faltan datos para codificar.");
        return;
    }
    var resultado = '';
    var indiceclave = 0;
    var charartexto = texto.value.split('')

    console.log(charartexto);
    for (let i = 0; i < charartexto.length; i++) {
        var desp = obindiceClave(clave.value.charAt(indiceclave));
        var chartexto = charartexto[i];

        console.log('chartexto:', chartexto);
        console.log('desp:', desp );
        console.log('desp2:', (desp >= 26) ? desp % 26 : desp)
        resultado += vigenere.encode(chartexto, (desp >= 26) ? desp % 26 : desp);
        indiceclave++;
        console.log('resultado:', resultado)
        if (indiceclave >= clave.value.length) {
            indiceclave = 0;
        }

        document.getElementById('resultado').value = resultado;
    }
}

function obindiceClave (reco) { 
    var abc = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
    return abc.indexOf(reco.toLowerCase());
}
//descifrar

function descifrar() {
    if (!Verificar()) {
        alert("Faltan datos para descifrar.");
        return;
    }
    var resultado = '';
    var indiceclave = 0;
    var charartexto = texto.value.split('');

    for (let i = 0; i < charartexto.length; i++) {
        var desp = obindiceClave(clave.value.charAt(indiceclave));
        var chartexto = charartexto[i];

        resultado += vigenere.decode(chartexto, (desp >= 26) ? desp % 26 : desp);
        indiceclave++;
        if (indiceclave >= clave.value.length) {
            indiceclave = 0;
        }
    }

    document.getElementById('resultado').value = resultado;
}

//reiniciar
function reiniciar() {
    texto.value = "";
    clave.value = "";
    document.getElementById('resultado').value = "";
}
//copiar
function copiarTexto() {
    const resultado = document.getElementById("resultado");
    if (resultado.value) {
        navigator.clipboard.writeText(resultado.value)
            .then(() => alert("Texto copiado al portapapeles."))
            .catch(() => alert("Error al copiar el texto."));
    } else {
        alert("No hay texto para copiar.");
    }
}

btn_Cifrar.addEventListener('click', cifrar);
document.getElementById('btn_Descifrar').addEventListener('click', descifrar);
document.getElementById('btn_Reiniciar').addEventListener('click', reiniciar);
document.getElementById('btn_Copiar').addEventListener('click', copiarTexto);