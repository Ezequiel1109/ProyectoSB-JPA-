package com.DavidERD.JPAdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.DavidERD.JPAdemo.Models.CategoriasModels;
import com.DavidERD.JPAdemo.Models.PerfilesModels;
import com.DavidERD.JPAdemo.Models.UsuariosModels;
import com.DavidERD.JPAdemo.Models.VacantesModels;
import com.DavidERD.JPAdemo.Repository.CategoriasRepository;
import com.DavidERD.JPAdemo.Repository.PerfilesRepository;
import com.DavidERD.JPAdemo.Repository.UsuariosRepository;
import com.DavidERD.JPAdemo.Repository.VacantesRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriasRepository repo;
	@Autowired
	private VacantesRepository repoVacantes;
	@Autowired
	private UsuariosRepository  repoUsuario;
	@Autowired
	private PerfilesRepository  repoPerfil;

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}
	/**
	 * EJEMPLOS PARA QUE FUNCIONE POR CONSOLA
	 */
	public void run(String... args) throws Exception {
		buscarPorVariosEstatus();
	}
	/** 
	 * JpaRepository Interface 
	* */
	private void buscarPorVariosEstatus(){
		System.out.println("Todas las vacantes: ");
		String[ ] estatus = new String[]{"Eliminada","Creada"};
		List<VacantesModels> lst = repoVacantes.findByEstatusIn(estatus);
		System.out.println("Registro encontrados: "+ lst.size());
		for (VacantesModels v : lst){
			System.out.println(v.getId() + ": " + v.getNombre() + ": " + v.getEstatus());
		}

	}

	//Metodos para vacantes
	/** 
	 * JpaRepository Interface 
	* */
	private void buscarVacantesPorSalario(){
		System.out.println("Buscando vacantes por Salario");
		List<VacantesModels> lst = repoVacantes.findBySalarioBetweenOrderBySalarioDesc( 7000 ,14000);
		System.out.println("Registro encontrados: "+ lst.size());
		for (VacantesModels v : lst){
			System.out.println(v.getId() + ": " + v.getNombre() + ":$ " + v.getSalario());
		}
	}
	/** 
	 * JpaRepository Interface 
	* */
	private void buscarVacantesPorEstatusDestacado(){
		System.out.println("Buscando vacantes por estatus y destacados");
		List<VacantesModels> lst = repoVacantes.findByDestacadoAndEstatusOrderByIdDesc( 1 ,"aprobada");
		System.out.println("Registro encontrados: "+ lst.size());
		for (VacantesModels v : lst){
			System.out.println(v.getId() + ": " + v.getNombre() + ": " + v.getEstatus()+ ": "+ v.getDestacado());
		}
	}
	/** 
	 * JpaRepository Interface 
	* */
	private void buscarVacantesPorEstatus(){
		System.out.println("Buscando vacantes por estatus");
		List<VacantesModels> lst = repoVacantes.findByEstatus("aprobada");
		System.out.println("Registro encontrados: "+ lst.size());
		for (VacantesModels v : lst){
			System.out.println(v.getId() + ": " + v.getNombre() + ": " + v.getEstatus());
		}
	}

	/** 
	 * JpaRepository Interface 
	* */
	private void buscarUsuario(){
		Optional<UsuariosModels> optional  = repoUsuario.findById(4);
		if (optional.isPresent()) {
			UsuariosModels  user = optional.get();
			System.out.println("Nombre: "+user.getNombre());
			System.out.println("Perfiles asignados");
			for (PerfilesModels p : user.getPerfiles()) {
				System.out.print("\t"+p.getPerfil()+"\n");
			}
		}else{
			System.err.println("No se ha encontrado el usuario");
		}
	}
	/** 
	 * JpaRepository Interface 
	* */
	private void crearUsuarioconPerfil(){
		UsuariosModels user =  new UsuariosModels();
		user.setNombre("David");
		user.setEmail("david123@gmail.com");
		user.setFechaRegistro(new Date());
		user.setUsername("davitox");
		user.setPassword("123456789");
		user.setEstatus(1);

		PerfilesModels perfil1 =  new PerfilesModels();
		perfil1.setId(2);

		PerfilesModels perfil2 = new PerfilesModels();
		perfil2.setId(3);

		user.agregar(perfil1);
		user.agregar(perfil2);

		repoUsuario.save(user);
	}

	/** 
	 * JpaRepository Interface 
	* */
	private void crearPerfiles(){
		repoPerfil.saveAll(getPerfilesModelsAplications());

	}

	private List<PerfilesModels> getPerfilesModelsAplications(){
		List<PerfilesModels> perfiles = new LinkedList<PerfilesModels>();
		PerfilesModels per1 = new PerfilesModels();
		per1.setPerfil("Supervisor");

		PerfilesModels per2 = new PerfilesModels();
		per2.setPerfil("Administrador");

		PerfilesModels per3 = new PerfilesModels();
		per3.setPerfil("Usuario");

		perfiles.add(per1);
		perfiles.add(per2);
		perfiles.add(per3);

		return perfiles;

	}
	/** 
	 * JpaRepository Interface 
	* */
	private void guardarVacantes(){
		VacantesModels vacantes =  new VacantesModels();
		vacantes.setNombre("Desarrollador Java");
		vacantes.setDescripcion("estamos realizando unas nuevas oportunidades laborales");
		vacantes.setFecha(new Date());
		vacantes.setSalario(500.00);
		vacantes.setEstatus("aprobada");
		vacantes.setDestacado(0);
		vacantes.setImagen("escuela.png");
		vacantes.setDetalles("<h1> necesitamos un programador para moldearlo </h1> ");
		
		CategoriasModels cat = new CategoriasModels();
		cat.setId(5);
		vacantes.setCategoria(cat);

		repoVacantes.saveAndFlush(vacantes); //Guardamos la nueva vacante y actualizamos el cache de datos
		System.out.println("\nSe ha Guardado con exito: " + vacantes);
	}
	/** 
	 * JpaRepository Interface 
	* */
	private void buscarVacantes(){
		List<VacantesModels> list = repoVacantes.findAll();
		for  (VacantesModels v : list){
			System.out.println(v.getId() + " " + v.getNombre() + " -> " + v.getCategoria().getNombre()); 
		}

	}

	//Metodos para categoria

	/** 
	 * JpaRepository Interface 
	* */
	private void buscarTodosPaginacionOrdenados(){
		System.out.println("---------BUSQUEDA TODOS LOS REGISTROS------------");
		Page<CategoriasModels> page = repo.findAll(PageRequest.of(0, 5, Sort.by("nombre"))); // Bus
		System.out.printf("\nTotal de Registros: %d\n",page.getTotalElements());
		System.out.printf("\nTotal de Paginas: %d\n",page.getTotalPages());
		for (CategoriasModels c : page.getContent()) {
			System.out.println(c.getId() + " - " + c.getNombre());
		}

	}
	/** 
	 * JpaRepository Interface 
	* */
	private void buscarTodosPaginacion(){
		System.out.println("---------BUSQUEDA TODOS LOS REGISTROS------------");
		Page<CategoriasModels> page = repo.findAll(PageRequest.of(0, 5));
		System.out.printf("\nTotal de Registros: %d\n",page.getTotalElements());
		System.out.printf("\nTotal de Paginas: %d\n",page.getTotalPages());
		for (CategoriasModels c : page.getContent()) {
			System.out.println(c.getId() + " - " + c.getNombre());
		}

	}
	/** 
	 * JpaRepository Interface 
	* */
	private void buscarTodasOrdenadas(){
		System.out.println("\n\nBuscando todas las categorías ordenadas por nombre:");
		List<CategoriasModels> resultado = repo.findAll(Sort.by("nombre").descending());	 // Ordena de Z a A
		resultado.forEach((cat)-> System.out.println("ID:"+ cat.getId() + " - Nombre: "+ cat.getNombre()));
	}
	/** 
	 * JpaRepository Interface 
	* */
	private void eliminarTodosJpa(){
		repo.deleteAllInBatch(); 	
	}
	/** 
	 * JpaRepository Interface 
	* */
	private void buscarTodasJpa(){
		System.out.println("Buscando todas las categorías");
		List<CategoriasModels> lista = repo.findAll();
		for(CategoriasModels c : lista){
			System.out.println(c.getId() + " - " + c.getNombre());
		}
		
	}

	/** 
	 * CrudRepository Interface 
	* */
	private void guardar(){
		CategoriasModels cat =  new CategoriasModels();
		cat.setNombre("Tecnologia");
		cat.setDescripcion("trabajos con tecnologias");
		repo.save(cat);
		System.out.println("Se ha Guardado la categoria: " + cat.getId()+ ", "+cat.getNombre());
	}
	/** 
	 * CrudRepository Interface 
	* */
	private void buscarPorId(){
		Optional<CategoriasModels> optional = repo.findById(1);

		if (optional.isPresent()) {
			CategoriasModels cat = optional.get();
			System.out.println("La categoria de id= " + cat.getId() + " tiene el nombre: " + cat.getNombre() +
			" y	la descripción es :"+cat.getDescripcion());
		} else {
			System.out.println("No se encontro una categoria con ese ID");
		}
	}
	/** 
	 * CrudRepository Interface 
	* */
	private void modificar(){
		Optional<CategoriasModels> optional = repo.findById(2);

		if (optional.isPresent()) {
			CategoriasModels cat = optional.get();
			cat.setNombre("Modificada Tecnología");
			cat.setDescripcion("tecnologis eficaces del nuevo orden");
			repo.save(cat);
			System.out.println("La categoria de id= " + cat.getId() + " tiene el nombre: " + cat.getNombre() +
			" y	la descripción es :"+cat.getDescripcion());

		} else {
			System.out.println("No se encontro una categoria con ese ID");
		}
	}
	/** 
	 * CrudRepository Interface 
	* */
	private void eliminar(){
		int idCategoria = 1;
		repo.deleteById(idCategoria);
		System.out.println("Se elimino la categoria de id= " + idCategoria);
	}
	/** 
	 * CrudRepository Interface 
	 * */
	private void conteo(){
		long count = repo.count();
		System.out.println("Hay " + count + " categorias registradas.");
	}
	/** 
	 * CrudRepository Interface 
	 * */	
	private void eliminartodas(){
		repo.deleteAll();
		System.out.println("Todas las categorias fueron borradas");
	}
	/** 
	 * CrudRepository Interface 
	 * */
	private void encontrarPorIDS() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(3);
		ids.add(4);
		ids.add(10);
		Iterable<CategoriasModels> listaCat = repo.findAllById(ids);	
		for (CategoriasModels c  : listaCat) {
			System.out.println("El nombre de la categoría es: "+c.getNombre());
		}
	
	}
	/** 
	 * CrudRepository Interface 
	 * */
	private void buscarTodos(){
		Iterable<CategoriasModels> listaCat = repo.findAll();
		for (CategoriasModels c : listaCat){
			System.out.print("ID: ");
			System.out.print(c.getId()+"\t");
			System.out.print("Nombre: ");
			System.out.println(c.getNombre());
			System.out.println("Descripcion: ");
			System.out.println(c.getDescripcion());			
		}
	}
	/** 
	 * CrudRepository Interface 
	 * */
	private void existeId(){
		Integer id = 10;
		boolean exite = repo.existsById(id);
		if(exite == true) {
			System.out.println("La categoria con el ID "+id+", SI Existe!");
		}else{
			System.out.println("La categoria con el ID "+id+", NO Existe!");
		}
	}
	/** 
	 * CrudRepository Interface 
	 * */
	private void guardarTodas(){
		List<CategoriasModels>  listacat = getListaCategorias();
		repo.saveAll(listacat);
	}

	private List<CategoriasModels> getListaCategorias() {
		CategoriasModels cat1 = new CategoriasModels();
		cat1.setNombre("Deportes");
		cat1.setDescripcion("Actividades al aire libre.");
		
		CategoriasModels cat2 = new CategoriasModels();
		cat2.setNombre("Juguetes");
		cat2.setDescripcion("Para niños pequeños.");
		
		List<CategoriasModels> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		return lista;
	}

	

}
