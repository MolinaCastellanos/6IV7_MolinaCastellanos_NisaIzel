var vigenere = vigenere || (function () {

    //Aqui tenemos que crear una funcion que se encarge de obtener el texto,
    //desplazamiento y si va a cifrar o descifrar

    var proceso = function (txt, desp, action) {
        var replace = (function(){
            //ABC
            var abc = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z'];
            var longitud = abc.length;

            return function(c) {
                var i = abc.indexOf(c.toLowerCase());
                if (i != -1) {
                    var pos = i;
                    if (action) {
                        //cifrar
                        pos += desp;
                        pos = (pos >= longitud) ? pos - 1 : pos;
                    } else {
                        // descifrar ahí lo hacen :3
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

//cuando la clave sea más grande que el texto para cifrar

//cuando la clave sea más grande que el texto para descifrar

//cifrar
function codificar(texto, clave){

    var resultado = '';
    var indiceclave = 0;
    var charartexto = texto.split('')

    for (let i = 0; i < charartexto.length; i++) {
        var desp = obindiceClave(clave.charAt(indiceclave));
        var chartexto = charartexto[i];

        resultado += vigenere.encode(chartexto, (desp >= 26) ? desp % 26 : desp);
        indiceclave++;
        
        if (indiceclave >= clave.length) {
            indiceclave = 0;
        }

        document.getElementById('resultado').value = resultado;
    }
}

function obindiceClave (reco) { 
    var abc = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z'];
    return abc.indexOf(reco.toLowerCase());
}
//descifrar

//reiniciar