package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnItemClickListener {

//    private lateinit var btnImc: LinearLayout
    private lateinit var rvMain: RecyclerView
    private lateinit var btnItem: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItens = mutableListOf<MainItem>()
        mainItens.add(
            MainItem(id = 1,
            drawableId = R.drawable.ic_baseline_wb_sunny_24,
            textStringId = R.string.label_imc,
            color = Color.GREEN
            )
        )
        mainItens.add(
            MainItem(id = 2,
                drawableId = R.drawable.ic_baseline_visibility_24,
                textStringId = R.string.tmb,
                color = Color.YELLOW
            )
        )

        val adapter = MaindAdapter(mainItens, this)
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }

    override fun OnClick(id: Int) {
        when(id){
            1 -> {
                val intent = Intent(this, ImcActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                //Abrir outra activity
            }
        }
        Log.i("Teste", "clicou no $id")
    }

    private inner class MaindAdapter(
        private val mainItens: List<MainItem>,
        private val onItemClickListener: OnItemClickListener
        ) : RecyclerView.Adapter<MainViewHolder>(){

        //Definindo o layout XML da célula específica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItens[position]
            holder.bind(itemCurrent)

        }

        override fun getItemCount(): Int {
            return mainItens.size
        }

    }

    private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: MainItem){
            val img: ImageView = itemView.findViewById(R.id.item_img_icon)
            val name: TextView = itemView.findViewById(R.id.item_txt_name)
            val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

            img.setImageResource(item.drawableId)
            name.setText(item.textStringId)
            container.setBackgroundColor(item.color)

            container.setOnClickListener{
                OnClick(item.id)
            }

        }
    }



}