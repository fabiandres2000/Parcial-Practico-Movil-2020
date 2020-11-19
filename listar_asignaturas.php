<?php  

include ('conexion.php');


$sentencia=$con->query("SELECT *from asignatura");

while ($fila = mysqli_fetch_array($sentencia)) {
	$json['asignatura'][]=$fila;
}
mysqli_close($con);

echo json_encode($json);
?>