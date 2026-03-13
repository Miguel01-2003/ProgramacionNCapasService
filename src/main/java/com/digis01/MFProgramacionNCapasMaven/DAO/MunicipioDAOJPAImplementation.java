package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Municipio;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOJPAImplementation implements IMunicipioJPA{

    
    @Autowired
    private EntityManager entityManager;    
    
    @Override
    public Resultado GetByEstado(int IdEstado) {
        Resultado resultado = new Resultado();
        
        try {
            
            TypedQuery<Municipio> consulta = entityManager.createQuery("FROM Municipio m WHERE m.Estado.IdEstado = :IdEstado", Municipio.class);
                consulta.setParameter("IdEstado", IdEstado);
            
            resultado.objects = new ArrayList<>();
            List<Municipio> municipios = consulta.getResultList();
            
            for (Municipio municipio : municipios) {
                resultado.objects.add(municipio);
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
    public Resultado GetById(int IdMunicipio) {
        throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
