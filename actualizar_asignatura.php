<?php  

include ('conexion.php');

if (isset($_GET['nombre']) &&  isset($_GET['codigo'])) {

  $codigo = $_GET['codigo'];
  $nombre = $_GET['nombre'];
  
  	$insert = $con->query("UPDATE `asignatura` SET `codigo`='$codigo',`nombre`='$nombre' WHERE `codigo`='$codigo'");
  	if ($insert) {
  		$resultar['res']='asignatura actualizada correctamente';
    $json['respuesta'][]=$resultar; 
  	}else{
  		$resultar['res']='error al actualizar la asignatura';
        $json['respuesta'][]=$resultar; 
  	} 	
  
}else{
	$resultar['res']='variables vacias';
    $json['respuesta'][]=$resultar; 	
}
echo json_encode($json);
$con->close();
?>