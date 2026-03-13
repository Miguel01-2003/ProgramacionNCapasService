package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOJPAImplementation implements IColoniaJPA{

    
    @Autowired
    private EntityManager entityManager;    
    
    @Override
    public Resultado GetByMunicipio(int IdMunicipio) {
        Resultado resultado = new Resultado();
        
        try {
            
            TypedQuery<Colonia> consulta = entityManager.createQuery("FROM Colonia c WHERE c.Municipio.IdMunicipio = :IdMunicipio", Colonia.class);
            consulta.setParameter("IdMunicipio", IdMunicipio); 
            
            resultado.objects = new ArrayList<>();
            List<Colonia> colonias = consulta.getResultList();
            
            for (Colonia colonia : colonias) {
                resultado.objects.add(colonia);
            }
            
            resultado.correcto = true;
            
            
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    public Resultado GetById(int IdColonia) {
        throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
