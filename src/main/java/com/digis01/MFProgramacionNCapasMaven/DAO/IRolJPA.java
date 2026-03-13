package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;

public interface IRolJPA {
    Resultado GetAll();
    
    Resultado GetByID(int IdRol);
}
