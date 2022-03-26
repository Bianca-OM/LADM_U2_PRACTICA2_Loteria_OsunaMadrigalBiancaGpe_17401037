package com.example.ladm_u2_practica2_loteria_osunamadrigalbiancagpe_17401037

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AlertDialog
import com.example.ladm_u2_practica2_loteria_osunamadrigalbiancagpe_17401037.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val vectorCartas = arrayOf(R.drawable.carta1, R.drawable.carta2, R.drawable.carta3,
        R.drawable.carta4, R.drawable.carta5, R.drawable.carta6, R.drawable.carta7,
        R.drawable.carta8, R.drawable.carta9, R.drawable.carta10, R.drawable.carta11,
        R.drawable.carta12, R.drawable.carta13, R.drawable.carta14, R.drawable.carta15,
        R.drawable.carta16, R.drawable.carta17, R.drawable.carta18, R.drawable.carta19,
        R.drawable.carta20, R.drawable.carta21, R.drawable.carta22, R.drawable.carta23,
        R.drawable.carta24, R.drawable.carta25, R.drawable.carta26, R.drawable.carta27,
        R.drawable.carta28, R.drawable.carta29, R.drawable.carta30, R.drawable.carta31,
        R.drawable.carta32, R.drawable.carta33, R.drawable.carta34, R.drawable.carta35,
        R.drawable.carta36, R.drawable.carta37, R.drawable.carta38, R.drawable.carta39,
        R.drawable.carta40, R.drawable.carta41, R.drawable.carta42, R.drawable.carta43,
        R.drawable.carta44, R.drawable.carta45, R.drawable.carta46, R.drawable.carta47,
        R.drawable.carta48, R.drawable.carta49, R.drawable.carta50, R.drawable.carta51,
        R.drawable.carta52, R.drawable.carta53, R.drawable.carta54)

    val vectorAudios = arrayOf(R.raw.carta1, R.raw.carta2, R.raw.carta3, R.raw.carta4,
        R.raw.carta5, R.raw.carta6, R.raw.carta7, R.raw.carta8, R.raw.carta9,
        R.raw.carta10, R.raw.carta11, R.raw.carta12, R.raw.carta13, R.raw.carta14,
        R.raw.carta15, R.raw.carta16, R.raw.carta17, R.raw.carta18, R.raw.carta19,
        R.raw.carta20, R.raw.carta21, R.raw.carta22, R.raw.carta23, R.raw.carta24,
        R.raw.carta25, R.raw.carta26, R.raw.carta27, R.raw.carta28, R.raw.carta29,
        R.raw.carta30, R.raw.carta31, R.raw.carta32, R.raw.carta33, R.raw.carta34,
        R.raw.carta35, R.raw.carta36, R.raw.carta37, R.raw.carta38, R.raw.carta39,
        R.raw.carta40, R.raw.carta41, R.raw.carta42, R.raw.carta43, R.raw.carta44,
        R.raw.carta45, R.raw.carta46, R.raw.carta47, R.raw.carta48, R.raw.carta49,
        R.raw.carta50, R.raw.carta51, R.raw.carta52, R.raw.carta53, R.raw.carta54)

    val vectorNombres = arrayOf("1.Gallo", "2.Diablito", "3.Dama", "4.Catrín", "5.Paraguas",
        "6.Sirena", "7.Escalera", "8.Botella", "9.Barril", "10.Árbol", "11.Melón",
        "12.Valiente", "13.Gorrito", "14.Muerte", "15.Pera", "16.Bandera", "17.Bandolón",
        "18.Violoncello", "19.Garza", "20.Pájaro", "21.Mano", "22.Bota", "23.Luna",
        "24.Cotorro", "25.Borracho", "26.Negrito", "27.Corazón", "28.Sandía", "29.Tambor",
        "30.Camarón", "31.Jaras", "32.Músico", "33.Araña", "34.Soldado", "35.Estrella",
        "36.Cazo", "37.Mundo", "38.Apache", "39.Nopal", "40.Alacrán", "41.Rosa",
        "42.Calavera", "43.Campana", "44.Cantarito", "45.Venado", "46.Sol", "47.Corona",
        "48.Chalupa", "49.Pino", "50.Pescado", "51.Palma", "52.Maceta", "53.Arpa", "54.Rana")

    var nombresBar = Array(54){""}

    var player: MediaPlayer? = null

    var contador = 0
    val scoope = CoroutineScope(Job() + Dispatchers.Main)
    var corrutinaHistorial = scoope.launch(EmptyCoroutineContext, CoroutineStart.LAZY){
        while (true){
            delay(2500)
            runOnUiThread {
                binding.txtHistorial.text = nombresBar[contador++]+
                        ", "+binding.txtHistorial.text.toString()
                if(contador == 54) pausarDespausarCorrutina()
            }
        }
    }

    var ini = false
    var reini = false

    lateinit var darBaraja: HiloBaraja

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtHistorial.movementMethod = ScrollingMovementMethod()

        darBaraja = HiloBaraja(binding.imgCarta, nombresBar,this)

        binding.btnEmpezar.setOnClickListener {
            var cadBtn1 = binding.btnEmpezar.text.toString()
            if(!ini){
                iniciarDar()
                player = MediaPlayer.create(this,R.raw.inicio)
                player?.start()
            }else {
                if(cadBtn1 == "PAUSAR") {
                    pausarDar()
                    return@setOnClickListener
                }
                if(cadBtn1 == "CONTINUAR"){
                    despausarDar()
                    return@setOnClickListener
                }
                if (cadBtn1 == "NUEVO JUEGO"){
                    reiniciarDar()
                    return@setOnClickListener
                }
                if(cadBtn1 == "EMPEZAR"){
                    darBaraja.despausarHilo()
                    reini = false
                    reiniCorrutina()
                    binding.btnEmpezar.text = "PAUSAR"
                    binding.btnEmpezar.setBackgroundColor(Color.rgb(94, 94, 94))
                    binding.txtEstado.text = "Dando baraja"
                    return@setOnClickListener
                }
            }
        }

        binding.btnTerminar.setOnClickListener {
            var cadBtn2 = binding.btnTerminar.text.toString()
            if(!ini || reini){
                AlertDialog.Builder(this).setMessage("No ha iniciado el juego").show()
            }else {
                if (cadBtn2 == "TERMINAR") {
                    player = MediaPlayer.create(this,R.raw.comprobar)
                    player?.start()

                    terminarDar()
                    return@setOnClickListener
                }
                if(cadBtn2 == "COMPROBAR"){
                    comprobarDar()
                    return@setOnClickListener
                }
                if(cadBtn2 == "PAUSAR"){
                    pausarComprobar()
                    return@setOnClickListener
                }
                if(cadBtn2 == "CONTINUAR"){
                    despausarComprobar()
                    return@setOnClickListener
                }
            }
        }

    }

    fun iniciarDar(){
        AlertDialog.Builder(this)
            .setMessage("BARAJEANDO...")
            .setPositiveButton("ACEPTAR", { d,i-> d.dismiss()
                darBaraja.barajear()
                darBaraja.start()
                ini = true

                binding.txtHistorial.text = ""
                corrutinaHistorial.start()

                binding.btnEmpezar.text = "PAUSAR"
                binding.btnEmpezar.setBackgroundColor(Color.rgb(94,94,94))
                binding.txtEstado.text = "Dando baraja"
            })
            .show()
    }

    fun pausarDar(){
        darBaraja.pausarHilo()

        if(corrutinaHistorial.isActive) {
            pausarDespausarCorrutina()
        }

        binding.btnEmpezar.text = "CONTINUAR"
        binding.btnEmpezar.setBackgroundColor(Color.rgb(77,194,252))
        binding.txtEstado.text = "Pausado"
    }

    fun pausarComprobar(){
        if(corrutinaHistorial.isActive) {
            pausarDespausarCorrutina()
        }

        darBaraja.pausarHilo()

        binding.btnTerminar.text = "CONTINUAR"
        binding.btnTerminar.setBackgroundColor(Color.rgb(77,194,252))
        binding.txtEstado.text = "Comprobar pausado"
    }

    fun despausarDar(){
        darBaraja.despausarHilo()

        pausarDespausarCorrutina()

        binding.btnEmpezar.text = "PAUSAR"
        binding.btnEmpezar.setBackgroundColor(Color.rgb(94,94,94))
        binding.txtEstado.text = "Dando baraja"
    }

    fun despausarComprobar(){
        darBaraja.despausarHilo()

        pausarDespausarCorrutina()

        binding.btnTerminar.text = "PAUSAR"
        binding.btnTerminar.setBackgroundColor(Color.rgb(94,94,94))
        binding.txtEstado.text = "Comprobando"
    }

    fun terminarDar(){
        if(corrutinaHistorial.isActive) {
            pausarDespausarCorrutina()
        }

        darBaraja.pausarHilo()

        binding.btnTerminar.text = "COMPROBAR"
        binding.btnTerminar.setBackgroundColor(Color.rgb(77, 194, 252))

        binding.btnEmpezar.text = "NUEVO JUEGO"
        binding.btnEmpezar.setBackgroundColor(Color.rgb(139, 195, 74))
        binding.txtEstado.text = "Compruebe el resto de las cartas\nO inicie un nuevo juego"
    }

    fun comprobarDar(){
        darBaraja.despausarHilo()

        pausarDespausarCorrutina()

        binding.btnTerminar.text = "PAUSAR"
        binding.btnTerminar.setBackgroundColor(Color.rgb(94,94,94))

        binding.txtEstado.text = "Comprobando"
    }

    fun reiniciarDar(){
        pausarDar()
        AlertDialog.Builder(this)
            .setMessage("¿INICIAR NUEVO JUEGO?")
            .setPositiveButton("SI", { d,i-> d.dismiss()
                darBaraja.pausarHilo()
                AlertDialog.Builder(this)
                    .setMessage("BARAJEANDO...")
                    .setPositiveButton("ACEPTAR", { d,i-> d.dismiss()
                        darBaraja.reini()
                        reini = true

                        player = MediaPlayer.create(this,R.raw.inicio)
                        player?.start()
                        
                        darBaraja.barajear()

                        if(corrutinaHistorial.isActive) {
                            pausarDespausarCorrutina()
                        }
                        binding.txtHistorial.text = ""

                        binding.btnEmpezar.text = "EMPEZAR"
                        binding.btnEmpezar.setBackgroundColor(Color.rgb(139, 195, 74))

                        binding.btnTerminar.text = "TERMINAR"
                        binding.btnTerminar.setBackgroundColor(Color.rgb(255, 78, 78))
                        binding.txtEstado.text = "Nuevo juego"
                    })
                    .show()
            })
            .setNegativeButton("CANCELAR", { d,i-> d.cancel() })
            .show()
    }

    fun finDar(){
        player = MediaPlayer.create(this,R.raw.fin)
        player?.start()

        reini = true

        binding.btnEmpezar.text = "NUEVO JUEGO"
        binding.btnEmpezar.setBackgroundColor(Color.rgb(139, 195, 74))

        binding.btnTerminar.text = "TERMINAR"
        binding.btnTerminar.setBackgroundColor(Color.rgb(255, 78, 78))
        binding.txtEstado.text = "Llegó al final"
    }

    fun reiniCorrutina() {
        contador = 0
        binding.txtHistorial.text = ""
        corrutinaHistorial = scoope.launch(EmptyCoroutineContext, CoroutineStart.LAZY) {
            while (true) {
                runOnUiThread {
                    binding.txtHistorial.text = nombresBar[contador++] +
                            ", " + binding.txtHistorial.text.toString()
                    if (contador == 54) pausarDespausarCorrutina()
                }
                delay(2500)
            }
        }
        corrutinaHistorial.start()
    }

    fun pausarDespausarCorrutina(){
        if (corrutinaHistorial.isActive) {
            corrutinaHistorial.cancel()
        } else if (corrutinaHistorial.isCancelled) {
            corrutinaHistorial = scoope.launch(EmptyCoroutineContext, CoroutineStart.LAZY) {
                while (true) {
                    runOnUiThread {
                        binding.txtHistorial.text = nombresBar[contador++] +
                                ", " + binding.txtHistorial.text.toString()
                        if (contador == 54) pausarDespausarCorrutina()
                    }
                    delay(2500)
                }
            }
            corrutinaHistorial.start()
        }
    }
}