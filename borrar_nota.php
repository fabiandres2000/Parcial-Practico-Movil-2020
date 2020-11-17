<?php  

include ('conexion.php');

if (!empty($_GET['id'])) {

    $id = $_GET['id'];
  	$delete = $con->query("DELETE FROM `nota` WHERE `id`=$id");
  	if ($delete) {
  		$resultar['res']='nota borrada correctamente';
        $json['respuesta'][]=$resultar; 
  	}else{
  		$resultar['res']='error al borrar la nota';
        $json['respuesta'][]=$resultar; 
  	} 	
  
}else{
	$resultar['res']='variables vacias';
    $json['respuesta'][]=$resultar; 	
}
echo json_encode($json);
$con->close();
?>