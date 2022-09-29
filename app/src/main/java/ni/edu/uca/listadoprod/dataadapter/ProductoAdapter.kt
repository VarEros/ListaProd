package ni.edu.uca.listadoprod.dataadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding
import ni.edu.uca.listadoprod.dataclass.Producto

class ProductoAdapter(val listProd: List<Producto>):
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
        inner class ProductoHolder(val binding: ItemlistaBinding) :
                RecyclerView.ViewHolder(binding.root) {
                    fun cargar(producto: Producto) {
                        with(binding) {
                            tvCodProd.text = producto.id.toString()
                            tvNombreProd.text = producto.nombre
                            tvPrecioProd.text = producto.precio.toString()
                        }
                    }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position])
    }

    override fun getItemCount(): Int = listProd.size
}