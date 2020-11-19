<?php  

include ('conexion.php');


$sentencia=$con->query("SELECT *from nota");

while ($fila = mysqli_fetch_array($sentencia)) {
	$json['notas'][]=$fila;
}
mysqli_close($con);

echo json_encode($json);
?>