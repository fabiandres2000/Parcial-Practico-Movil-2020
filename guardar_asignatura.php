<?php  

include ('conexion.php');

$codigoo=$_GET['codigo'];
$nombree=$_GET['nombre'];

if (isset($codigoo) &&  isset($nombree)) {

  
  	$insert = $con->query("INSERT INTO `asignatura`(`codigo`, `nombre`) VALUES ('$codigoo','$nombree')");
  	if ($insert) {
  		$resultar['res']='asignatura guardada correctamente';
    $json['respuesta'][]=$resultar; 
  	}else{
  		$resultar['res']='error al guardar la asignatura';
        $json['respuesta'][]=$resultar; 
  	} 	
  
}else{
	$resultar['res']='variables vaciass';
    $json['respuesta'][]=$resultar; 	
}
echo json_encode($json);
$con->close();
?>