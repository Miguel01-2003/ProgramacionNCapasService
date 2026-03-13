package com.digis01.MFProgramacionNCapasMaven.RestController;

import com.digis01.MFProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis01.MFProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.MFProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import com.digis01.MFProgramacionNCapasMaven.JPA.Rol;
import com.digis01.MFProgramacionNCapasMaven.JPA.Usuario;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("api/cargaMasiva")
public class CargaMasivaRestController {
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation; 
    
    @PostMapping("/usuario")
    public ResponseEntity AddUsuarios(@RequestPart("archivo") MultipartFile archivo, HttpSession session){
        Resultado resultado = new Resultado();
        try {

            if(archivo == null || archivo.isEmpty()){
                resultado.correcto = false;
                resultado.mensajeError = "Archivo vacio";
                return ResponseEntity.badRequest().body(resultado);
            }
            
            String rutaBase = System.getProperty("user.dir");
            String rutaCarpeta = "src/main/resources/archivosCM";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
            String nombreArchivo = fecha + archivo.getOriginalFilename();
            String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
            String nombreOriginal = archivo.getOriginalFilename();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf(".")+1);
            
            File archivoGuardado = new File(rutaArchivo);
            archivo.transferTo(archivoGuardado);
            
            if(extension.equalsIgnoreCase("txt")){
                List<Usuario> usuarios = LecturaArchivoTXT(archivoGuardado);
            }else if(extension.equalsIgnoreCase("xlsx")){
                
            }else{
                resultado.correcto = false;
                resultado.mensajeError = "Formato de archivo invalido";
                return ResponseEntity.badRequest().body(resultado);
            }
           
            if(resultado.correcto){
                //Validaciones
                
                String hash = SHA256(rutaArchivo).object.toString();
                resultado.object = hash;
                session.setAttribute(hash, rutaArchivo);
                return ResponseEntity.ok(resultado);
            }
            
            return ResponseEntity.badRequest().body(resultado);
                          
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
            return ResponseEntity.badRequest().body(resultado);
        }      
    } 
    
    
    @PostMapping("/procesar/{llave}")
    public ResponseEntity Procesar(@PathVariable("llave") String llave, HttpSession session){
        try{
            
            Resultado resultado = new Resultado();
            String rutaArchivo = session.getAttribute(llave).toString();

            File archivo = new File(rutaArchivo);

            String nombreOriginal = archivo.getName();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf(".")+1);

            if(extension.equalsIgnoreCase("txt")){
                List<Usuario> usuarios = LecturaArchivoTXT(archivo);
                resultado = usuarioDAOJPAImplementation.AddAll(usuarios);

            }else if(extension.equalsIgnoreCase("xlsx")){

            }else{
                resultado.correcto = false;
                resultado.mensajeError = "Formato de archivo invalido";
                return ResponseEntity.badRequest().body(resultado);
            }
            
            if(resultado.correcto){
                return ResponseEntity.ok(resultado);
            }else{
                return ResponseEntity.badRequest().body(resultado);
            }
            
            
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex);
        }

    }
    
    public List<Usuario> LecturaArchivoTXT(File archivo){
        Resultado resultado = new Resultado();
        List<Usuario> usuarios = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(archivo);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            
           
            
            String columna = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
            simpleDateFormat.setLenient(false);
            while ((columna = bufferedReader.readLine()) != null) {
                System.out.println(columna);
                String[] atributosColumna = columna.split("\\|");
                Usuario usuario = new Usuario();
                usuario.Rol = new Rol();
                usuario.Direcciones = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.Colonia = new Colonia();
                
                usuario.setUserName(atributosColumna[0]);
                
                usuario.setNombre(atributosColumna[1]);
                usuario.setApellidoPaterno(atributosColumna[2]);
                if(atributosColumna[3].length()>0){
                     usuario.setApellidoMaterno(atributosColumna[3]);//Opcional
                }
                usuario.setEmail(atributosColumna[4]);
                usuario.setPassword(atributosColumna[5]);              
                usuario.setFechaNacimiento(simpleDateFormat.parse(atributosColumna[6]));
                usuario.setSexo(atributosColumna[7]);
                usuario.setTelefono(atributosColumna[8]);
                
                if(atributosColumna[9].length()>0){
                    usuario.setCelular(atributosColumna[9]);//Opcional
                }
                if(atributosColumna[10].length()>0){
                    usuario.setCURP(atributosColumna[10]);//Opcional
                }
                
                usuario.Rol.setIdRol(Integer.parseInt(atributosColumna[11]));
                
                direccion.setCalle(atributosColumna[12]);
                
                if(atributosColumna[13].length()>0){
                    direccion.setNumeroInterior(atributosColumna[13]);//Opcional
                }
                
                direccion.setNumeroExterior(atributosColumna[14]);
                direccion.Colonia.setIdColonia(Integer.parseInt(atributosColumna[15]));
                
                usuario.Direcciones.add(direccion);
                
                usuarios.add(usuario);
            }
            
            resultado.objects = new ArrayList<>(usuarios);
            
            
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return usuarios;
    }
    
    public Resultado SHA256(String ruta){
        Resultado resultado = new Resultado();
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(ruta.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder stringBuilder = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if(hex.length() == 1){
                    stringBuilder.append('0');
                    stringBuilder.append(hex);
                }
            }
            resultado.object = stringBuilder.toString();
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }
    
}
