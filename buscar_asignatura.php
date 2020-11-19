<?php  

include ('conexion.php');

$codigo = $_GET['codigo'];

$sentencia=$con->query("SELECT *from asignatura where codigo = '$codigo'");

while ($fila = mysqli_fetch_array($sentencia)) {
	$json['asignatura'][]=$fila;
}
mysqli_close($con);

echo json_encode($json);
?>