package com.digis01.MFProgramacionNCapasMaven.RestController;

import com.digis01.MFProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import com.digis01.MFProgramacionNCapasMaven.JPA.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoRestController {
    
    
    @Autowired
    private UsuarioDAOJPAImplementation usuariosDAOJPAImplementation;
    
    
    @GetMapping
    public String HolaMundo(){
        return "HolaMundo";
    }
    
    @GetMapping("saludo/{nombre}")
    public String Saludo(@PathVariable("nombre") String nombre){
        return "Hola " + nombre;
    }
    
    @GetMapping("saludo")
    public String Saludo2(@RequestParam("nombre") String nombre){
        return "Hola " + nombre;
    }
    
    @GetMapping("usuarios/{status}")
    public ResponseEntity ObtenerDatos(@PathVariable("status") int status){
        List<Usuario> usuarios = new ArrayList<>();
        
        Resultado resultado = usuariosDAOJPAImplementation.GetAll();
        
         for (Object object : resultado.objects) {
             usuarios.add((Usuario)object);
         }
        
        return ResponseEntity.status(status).body(usuarios);
    }
    
    @GetMapping("suma/{numero1}/{numero2}")
    public String Suma(@PathVariable("numero1") int numero1, @PathVariable("numero2") int numero2){
        
        int resultado = numero1 + numero2;
        
        return "La suma de " + numero1 + " y " + numero2 + " es " + resultado;
    }
    
    @PostMapping("suma")
    public String Suma2(@RequestBody List<Integer> numeros){
        
        int resultado = 0;
        
        String cadena = "La suma de ";
        for (Integer numero : numeros) {
            resultado = resultado + numero;
            cadena = cadena + numero+", ";
        }
        cadena = cadena + "es "+resultado;
        
        
        return cadena;
    }
}
