package com.digis01.MFProgramacionNCapasMaven.RestController;

import com.digis01.MFProgramacionNCapasMaven.DAO.RolDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rol")
public class RolRestController {
    
    @Autowired
    private RolDAOJPAImplementation rolDAOJPAImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){
        try {           
            Resultado resultado = rolDAOJPAImplementation.GetAll();
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
}
