<?php 
 $host_db = "localhost";
 $user_db = "id15282016_fabiandres";
 $pass_db = "cuentafalsa17-A";
 $db_name = "id15282016_parcial";
 

 $con = new mysqli($host_db, $user_db, $pass_db, $db_name);

    if ($con->connect_error) {
        die("La conexion falló: " . $conexion->connect_error);
    }

?>