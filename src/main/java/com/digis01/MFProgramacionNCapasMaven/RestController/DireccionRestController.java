package com.digis01.MFProgramacionNCapasMaven.RestController;

import com.digis01.MFProgramacionNCapasMaven.DAO.ColoniaDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.DireccionDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.EstadoDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.MunicipioDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.DAO.PaisDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/direccion")
public class DireccionRestController {
    
    @Autowired
    private DireccionDAOJPAImplementation direccionDAOJPAImplementation;
    
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    
    @Autowired
    private MunicipioDAOJPAImplementation municipioDAOJPAImplementation;
    
    @Autowired
    private ColoniaDAOJPAImplementation coloniaDAOJPAImplementation;
    
    @Autowired
    private PaisDAOJPAImplementation paisDAOJPAImplementation;
    
    @GetMapping
    public ResponseEntity GetAllPaises(){
        try {           
            Resultado resultado = paisDAOJPAImplementation.GetALL();
            if(resultado.correcto){
                if(resultado.objects != null || !resultado.objects.isEmpty()){
                    return ResponseEntity.ok(resultado);
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
    
    @GetMapping("/{IdDireccion}")
    public ResponseEntity GetById(@PathVariable("IdDireccion") int IdDireccion){
        try {           
            Resultado resultado = direccionDAOJPAImplementation.GetById(IdDireccion);           
            if(resultado.correcto){
                if(resultado.object != null){               
                    return ResponseEntity.ok(resultado);                   
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
    
    @GetMapping("/GetEstadosByPais/{IdPais}")
    public ResponseEntity GetEstadosByPais(@PathVariable("IdPais") int IdPais){     
        try {           
            Resultado resultado = estadoDAOJPAImplementation.GetByPais(IdPais);           
            if(resultado.correcto){
                if(resultado.objects != null){               
                    return ResponseEntity.ok(resultado);                   
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
    
    @GetMapping("/GetMunicipiosByEstado/{IdEstado}")
    public ResponseEntity GetMunicipiosByEstado(@PathVariable("IdEstado") int IdEstado){
        try {           
            Resultado resultado = municipioDAOJPAImplementation.GetByEstado(IdEstado);
            if(resultado.correcto){
                if(resultado.objects != null){               
                    return ResponseEntity.ok(resultado);                   
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
    
    @GetMapping("/GetColoniasByMunicipio/{IdMunicipio}")
    public ResponseEntity GetColoniasByMunicipio (@PathVariable("IdMunicipio") int IdMunicipio){
        try {           
            Resultado resultado = coloniaDAOJPAImplementation.GetByMunicipio(IdMunicipio);
            if(resultado.correcto){
                if(resultado.objects != null){               
                    return ResponseEntity.ok(resultado);                   
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
    
    @DeleteMapping("/{IdDireccion}")
     public ResponseEntity Delete(@PathVariable("IdDireccion") int IdDireccion){
        try {
            Resultado resultado = direccionDAOJPAImplementation.Delete(IdDireccion);
            if(resultado.correcto){
                return ResponseEntity.ok(resultado);
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }    
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }
     
    @PostMapping("/{IdUsuario}")
    public ResponseEntity add(@RequestBody Direccion direccion, @PathVariable("IdUsuario") int Idusuario){
        try {
            Resultado resultado = direccionDAOJPAImplementation.Add(direccion, Idusuario);
            if(resultado.correcto){
                return ResponseEntity.ok(resultado);
            }else{
                return ResponseEntity.badRequest().body(resultado.mensajeError);
            }    
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }
    
    @PutMapping("/{IdDireccion}")
    public ResponseEntity Update(@RequestBody Direccion direccion, @PathVariable("IdDireccion") int IdDireccion){
        try {
            Resultado resultado = direccionDAOJPAImplementation.Update(direccion, IdDireccion);
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
