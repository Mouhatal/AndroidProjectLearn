<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once './DBManager.php';
    include_once './personne.php';

    // $database = new Database();
    // $db = $database->getConnection();

    $item = new Personne();

    $data = json_decode(file_get_contents("php://input"));

    $item->NomPrenomPersonne = $data->NomPrenomPersonne;
    $item->AdressePersonne = $data->AdressePersonne;
    $item->TelPersonne = $data->TelPersonne;
    $item->EmailPersonne = $data->EmailPersonne;
    $item->DateNaissancePersonne = date('Y-m-d H:i:s');

    if($item->save()){
        echo json_encode(
            array("ajout" => "Ok")
        );
    } else{
        echo json_encode(
            array("ajout" => "Non")
        );
    }
