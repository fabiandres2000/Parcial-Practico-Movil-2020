<?php  

include ('conexion.php');

if (!empty($_GET['codigo'])) {

    $codigo = $_GET['codigo'];
  
  	$insert = $con->query("DELETE FROM `asignatura` WHERE `codigo`='$codigo'");
  	if ($insert) {
  		$resultar['res']='asignatura borrada correctamente';
        $json['respuesta'][]=$resultar; 
  	}else{
  		$resultar['res']='error al borrada la asignatura';
        $json['respuesta'][]=$resultar; 
  	} 	
  
}else{
	$resultar['res']='variables vacias';
    $json['respuesta'][]=$resultar; 	
}
echo json_encode($json);
$con->close();
?>