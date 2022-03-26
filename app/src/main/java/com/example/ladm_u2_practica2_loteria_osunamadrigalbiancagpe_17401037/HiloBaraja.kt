package com.example.ladm_u2_practica2_loteria_osunamadrigalbiancagpe_17401037

import android.media.MediaPlayer
import android.widget.ImageView
import kotlin.random.Random

class HiloBaraja(imagen: ImageView, nombres: Array<String>, activity: MainActivity) : Thread() {
    private var img = imagen
    private var act = activity
    private var nombresBar = nombres

    private var contador = 0
    private var ejecutar = true
    private var pausar = false

    private lateinit var cartasOrd: ArrayList<Int>
    private lateinit var audiosOrd: ArrayList<Int>
    private lateinit var nombresOrd: ArrayList<String>
    private var cartasBar = Array(54){0}
    private var audiosBar = Array(54){0}

    override fun run() {
        super.run()

        while (ejecutar){
            if(!pausar){
                //Si NO (!) PAUSADO
                act.runOnUiThread {
                    if(contador<54) {
                        img.setImageResource(cartasBar[contador])

                        act.player = MediaPlayer.create(act,audiosBar[contador])
                        act.player?.start()

                        contador++
                    }else{
                        pausarHilo()
                        reini()
                        act.finDar()
                    }
                }
                sleep(2500)
            }
        }
    }

    fun terminarHilo(){
        ejecutar = false
    }

    fun pausarHilo(){
        pausar = true
    }

    fun despausarHilo(){
        pausar = false
    }

    fun estaPausado() : Boolean {
        return pausar
    }

    fun barajear(){
        var flag = 0
        cartasOrd = ArrayList<Int>(act.vectorCartas.toMutableList())
        audiosOrd = ArrayList<Int>(act.vectorAudios.toMutableList())
        nombresOrd = ArrayList<String>(act.vectorNombres.toMutableList())
        while(cartasOrd.size>0){
            var rand = Random.nextInt(cartasOrd.size)
            cartasBar[flag]=cartasOrd[rand]
            audiosBar[flag]=audiosOrd[rand]
            nombresBar[flag]=nombresOrd[rand]

            cartasOrd.removeAt(rand)
            audiosOrd.removeAt(rand)
            nombresOrd.removeAt(rand)

            flag++
        }
        img.setImageResource(cartasBar[0])
    }

    fun reini(){
        contador=0
    }

    fun getCont(): Int{
        return contador
    }
}