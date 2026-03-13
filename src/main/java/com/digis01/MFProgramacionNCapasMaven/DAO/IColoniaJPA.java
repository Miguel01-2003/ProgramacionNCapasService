package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;

public interface IColoniaJPA {
    Resultado GetByMunicipio(int IdMunicipio);
    
    Resultado GetById(int IdColonia);
}
