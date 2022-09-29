package ni.edu.uca.listadoprod

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.listadoprod.dataadapter.ProductoAdapter
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding
import ni.edu.uca.listadoprod.dataclass.Producto

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    private var idContar = 0
    private var editMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    private fun iniciar() {
        with(binding) {
            etID.setText(idContar.toString())
            btnAgregar.setOnClickListener {
                if(!editMode)
                    agregarProd()
                else
                    applyChanges()
            }
            btnlimpiar.setOnClickListener {
                limpiar()
            }
        }
    }

    private fun limpiar() {
        with(binding) {
            etID.setText(idContar.toString())
            etPrecio.setText("")
            etNombreProd.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {
        with(binding) {
            try {
                val id: Int = idContar
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
                idContar++
            } catch (ex: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${ex.toString()}", Toast.LENGTH_SHORT).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                {producto ->  setEditMode(producto)},
                {producto -> eliminarConfirm(producto)})
            limpiar()
        }
    }

    private fun eliminarConfirm(producto: Producto) {
        var alerta = AlertDialog.Builder(this)
        alerta.setMessage("¿Desea eliminar este producto?")
            .setCancelable(false).setPositiveButton("Aceptar", DialogInterface.OnClickListener {
                    _, _ -> eliminarProd(producto)
            })
            // negative button text and action
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener {
                    _, _ ->
            })
        val dialogo = alerta.create()
        dialogo.setTitle("Eliminar Producto")
        dialogo.show()
    }

    private fun eliminarProd(producto: Producto){
        try{
            listaProd.remove(producto)
            binding.rcvLista.adapter = ProductoAdapter(listaProd,
                {producto -> setEditMode(producto)},
                {producto -> eliminarConfirm(producto)})
        }catch (ex : Exception){
            Toast.makeText(this@MainActivity,"Error ${ex.toString()}",Toast.LENGTH_LONG).show()
        }
    }

    private fun setEditMode(producto: Producto) {
        try {
            with(binding){
                etID.setText(producto.id.toString())
                etNombreProd.setText(producto.nombre.toString())
                etPrecio.setText(producto.precio.toString())
                btnAgregar.setText("Finalizar")
                editMode = true
            }
        }catch (ex : Exception){
            Toast.makeText(this@MainActivity,"Error ${ex.toString()}",Toast.LENGTH_LONG).show()
        }
    }

    private fun applyChanges(){
        try {
            with(binding){
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd[id] = prod
                rcvLista.adapter = ProductoAdapter(listaProd,
                    {producto ->  setEditMode(producto)},
                    {producto -> eliminarConfirm(producto)})
            }
            editMode = false
        }catch (ex : Exception){
            Toast.makeText(this@MainActivity,"Error ${ex.toString()}",Toast.LENGTH_LONG).show()
        }
        binding.etID.setText(idContar)
        binding.btnAgregar.setText("Agregar")
        limpiar()
    }
}