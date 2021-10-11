<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once './DBManager.php';
    include_once './personne.php';


    if(isset($_GET['IdPersonne'])) {
        $personne = Personne::getPersonneById($_GET['IdPersonne']);
        echo json_encode($personne);
     }
