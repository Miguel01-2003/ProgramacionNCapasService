package com.digis01.MFProgramacionNCapasMaven.RestController;

import com.digis01.MFProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import com.digis01.MFProgramacionNCapasMaven.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {
    
    
    @Autowired
    private UsuarioDAOJPAImplementation usuariosDAOJPAImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){             
        try {           
            Resultado resultado = usuariosDAOJPAImplementation.GetAll();
            if(resultado.correcto){
                if(resultado.objects != null || !resultado.objects.isEmpty()){
                    return ResponseEntity.ok(resultado.objects);
                }else{
                    return ResponseEntity.noContent().build();
                }
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }           
        } catch (Exception ex) {            
            return ResponseEntity.badRequest().body(ex);           
        }        
    }
    
    @GetMapping("/{IdUsuario}")
    public ResponseEntity GetById(@PathVariable("IdUsuario") int IdUsuario){        
        try {           
            Resultado resultado = usuariosDAOJPAImplementation.GetById(IdUsuario);           
            if(resultado.correcto){
                if(resultado.object != null){               
                    return ResponseEntity.ok(resultado.object);                   
                }else{                   
                    return ResponseEntity.noContent().build();                   
                }
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }               
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
        
    }
    
    @PostMapping
    public ResponseEntity Add(@RequestBody Usuario Usuario){
        try {
            Resultado resultado = usuariosDAOJPAImplementation.Add(Usuario);
            if(resultado.correcto){
                return ResponseEntity.ok(resultado);
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }                 
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);  
        }      
    }
    
    @PutMapping
    public ResponseEntity Update(@RequestBody Usuario Usuario){
        try {
            Resultado resultado = usuariosDAOJPAImplementation.Update(Usuario);
            if(resultado.correcto){
                return ResponseEntity.ok(resultado);
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }     
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }
    
    @DeleteMapping("/{IdUsuario}")
    public ResponseEntity Delete(@PathVariable("IdUsuario") int IdUsuario){
        try {
            Resultado resultado = usuariosDAOJPAImplementation.Delete(IdUsuario);
            if(resultado.correcto){
                return ResponseEntity.ok(resultado);
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }    
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }
    
    @PatchMapping("/status/{IdUsuario}/{status}")
    public ResponseEntity UpdateStatus(@PathVariable("IdUsuario") int IdUsuario, 
            @PathVariable("status") int status){
        try {
            Resultado resultado = usuariosDAOJPAImplementation.UpdateStatus(IdUsuario, status);
            if(resultado.correcto){
                return ResponseEntity.ok(resultado);
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }      
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }
    
    @PatchMapping("/imagen/{IdUsuario}/{imagen}")
    public ResponseEntity UpdateImagen(@PathVariable("IdUsuario") int IdUsuario,
            @PathVariable("imagen") String imagen){
        try {
            Resultado resultado = usuariosDAOJPAImplementation.UpdateImagen(imagen, IdUsuario);
            if(resultado.correcto){
                return ResponseEntity.ok(resultado);
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }    
        } catch (Exception ex) {
             return ResponseEntity.badRequest().body(ex);
        } 
    }
}
