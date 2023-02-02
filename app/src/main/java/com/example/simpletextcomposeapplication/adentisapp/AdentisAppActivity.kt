package com.example.simpletextcomposeapplication.adentisapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.databinding.ActivityLayoutGenericBinding
import com.example.simpletextcomposeapplication.databinding.ListItemBinding

class AdentisAppActivity : ComponentActivity() {

    private lateinit var binding: ActivityLayoutGenericBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLayoutGenericBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val baseNames = listOf(
            "Marco;M",
            "Maria;F",
            "Joao;M",
            "Luis;M",
            "Ana;F",
            "Isabel;M",
            "Ana;F",
            "Rita;I",
            "Luis;M",
            "Catarina;F",
            "Paulo;M",
            "Marina;F",
            "Luisa;F",
            "Marcia;F",
            "Pedro;M",
            "Joel;I",
            "Antonio;M",
            "Marisa;F",
            "Sofia;F",
            "Jose;M",
            "Patricia;F",
            "Paulo;M",
            "Marisa;F"
        )

        /**
         *
         *
         * Descrição:
         *
         * Mostrar o conteúdo da lista numa Recycler View, indicando quantidade de vezes
         * que repete o nome, texto para indicar se é masculino, feminino ou indefinido. Ordenado
         * alfabeticamente (por defeito) e permitir modifcar por numero de ocorrencias.
         * Identificar com uma cor distinta por cada genero (masculino, feminino, indefinido).
         * Mostrar o conteúdo das lista numa Recycler View.
         *
         * */
        val namesData = mutableMapOf<GenderProfile, Int>()
        baseNames
            .map { nameGender ->
                val split = nameGender.split(';')
                GenderProfile(split[0], split[1])
            }
            .map { profile ->
                if (namesData.containsKey(profile)) {
                    val oldValue = namesData[profile] ?: -1
                    namesData.put(profile, oldValue + 1)
                } else {
                    namesData.put(profile, 1)
                }
            }
        val sortedNamesData = namesData
            .toSortedMap(compareBy<GenderProfile> { it.name }.thenBy { namesData[it] })
            .map { GenderProfile(it.key.name, it.key.gender, it.value) }

        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = MyRecyclerView(
            sortedNamesData,
            arrayOf(
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.teal_200),
                ContextCompat.getColor(this, R.color.purple_200)
            )
        )
    }
}

data class GenderProfile(val name: String, val gender: String, val numberOfOccurrences: Int = -1)

class MyRecyclerView(private val profileData: List<GenderProfile>, private val bgColors: Array<Int>) : RecyclerView.Adapter<MyRecyclerView.MyViewHolder>() {
    class MyViewItems(val mytext: TextView)
    class MyViewHolder(mainView: View, val items: MyViewItems) : RecyclerView.ViewHolder(mainView)

    override fun getItemCount() = profileData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view.rootView, MyViewItems(view.findViewById(R.id.my_text)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.items.mytext.text = "${profileData[position].name}: ${profileData[position].numberOfOccurrences}"
        holder.items.mytext.setBackgroundColor(when (profileData[position].gender) {
            "M" -> bgColors[1]
            "F" -> bgColors[2]
            else -> bgColors[0]
        })
    }
}

