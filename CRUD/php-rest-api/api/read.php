<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");

    include_once './DBManager.php';
    include_once './personne.php';


    $personnes= Personne::find();
    echo json_encode($personnes);
