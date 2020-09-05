package qboinstitute.com.appfirebasekotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.TextureView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    var tipo : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        mostrarInfoAutenticacion()
    }

    fun mostrarInfoAutenticacion(){
        val bundle: Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        tipo = bundle?.getString("tipo").toString()
        val urlimagen : String? = bundle?.getString("urlimg")
        val nombre : String? = bundle?.getString("nombre")
        val preferencia : SharedPreferences.Editor =
            getSharedPreferences("appQBO", Context.MODE_PRIVATE).edit()
        preferencia.putString("email", email)
        preferencia.putString("nombre", nombre)
        preferencia.putString("urlimg", urlimagen)
        preferencia.putString("tipo", tipo)
        preferencia.apply()
        val tvnomusuario :TextView = navView.getHeaderView(0)
            .findViewById(R.id.tvnomusuario)
        val tvemailusuario :TextView = navView.getHeaderView(0)
            .findViewById(R.id.tvemailusuario)
        val ivusuario :ImageView = navView.getHeaderView(0)
            .findViewById(R.id.ivusuario)
        tvnomusuario.text = nombre
        tvemailusuario.text = email
        if(tipo != TipoAutenticacion.FIREBASE.name){
            Picasso.get().load(urlimagen).into(ivusuario)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}