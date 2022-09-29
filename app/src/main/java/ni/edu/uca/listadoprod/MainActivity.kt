package ni.edu.uca.listadoprod

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.listadoprod.dataadapter.ProductoAdapter
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding
import ni.edu.uca.listadoprod.dataclass.Producto

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    private fun iniciar() {
        with(binding) {
            btnAgregar.setOnClickListener {
                agregarProd()
            }
            btnlimpiar.setOnClickListener {
                limpiar()
            }
        }
    }

    private fun limpiar() {
        with(binding) {
            etID.setText("")
            etPrecio.setText("")
            etNombreProd.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {
        with(binding) {
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)

            } catch (ex: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${ex.toString()}", Toast.LENGTH_SHORT).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd)
            limpiar()
        }
    }
}