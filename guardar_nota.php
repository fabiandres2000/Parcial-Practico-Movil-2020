<?php  

include ('conexion.php');

if (!empty($_GET['corte1']) &&  !empty($_GET['corte2'])&&  !empty($_GET['corte3'])&&  !empty($_GET['nota'])&&  !empty($_GET['asignatura'])) {

  $corte1 = $_GET['corte1'];
  $corte2 = $_GET['corte2'];
  $corte3 = $_GET['corte3'];
  $asignatura = $_GET['asignatura'];
  $nota = $_GET['nota'];
  
  	$insert = $con->query("INSERT INTO `nota`(`ASIGNATURA`, `CORTE1`, `CORTE2`, `CORTE3`, `NOTA`) VALUES ('$asignatura',$corte1,$corte2,$corte3,$nota)");
  	
  	if ($insert) {
  		$resultar['res']='nota guardada correctamente';
        $json['respuesta'][]=$resultar; 
  	}else{
  		$resultar['res']='error al guardar la nota';
        $json['respuesta'][]=$resultar; 
  	} 	
  
}else{
	$resultar['res']='variables vacias';
    $json['respuesta'][]=$resultar; 	
}
echo json_encode($json);
$con->close();
?>