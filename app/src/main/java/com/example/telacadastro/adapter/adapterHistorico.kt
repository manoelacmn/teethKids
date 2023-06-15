import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.telacadastro.R
import com.example.telacadastro.adapter.AdapterAvaliacao
import com.example.telacadastro.adapter.AdpterPerfil
import com.example.telacadastro.model.Historico

class AdapterHistorico(private val context: Context, private  val Historico:MutableList<Historico>,val listener:Myclicklistenner):
    RecyclerView.Adapter<AdapterHistorico.HistoricoViewHolder>(){
    inner class HistoricoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome:TextView = itemView.findViewById<TextView>(R.id.nome)
        val descricao:TextView = itemView.findViewById<TextView>(R.id.desc)
        val datahora: TextView = itemView.findViewById<TextView>(R.id.datahora)
        init{
            itemView.setOnClickListener{
                val position= adapterPosition
                listener.onClick(position)
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val itemList = LayoutInflater.from(context).inflate(R.layout.recycler_hist_accept,parent,false)
        val holder = HistoricoViewHolder(itemList)
        return  holder

    }

    override fun getItemCount(): Int {
        return Historico.size
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        holder.nome.text = Historico[position].nome
        holder.descricao.text = Historico[position].desc
        holder.datahora.text = Historico[position].datahora
    }
    interface  Myclicklistenner{
        fun onClick(position: Int)
    }
}
