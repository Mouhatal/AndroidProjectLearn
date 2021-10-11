<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once './DBManager.php';
    include_once './personne.php';



    $item = new Personne();

    $data = json_decode(file_get_contents("php://input"));

    $item->IdPersonne = $data->IdPersonne;

    // personne values
    $item->NomPrenomPersonne = $data->NomPrenomPersonne;
    $item->AdressePersonne = $data->AdressePersonne;
    $item->TelPersonne = $data->TelPersonne;
    $item->EmailPersonne = $data->EmailPersonne;
    $item->DateNaissancePersonne = date('Y-m-d H:i:s');
    //$item->DateNaissancePersonne = $data->DateNaissancePersonne;

    if($item->update($item->IdPersonne)){
        echo json_encode(
            array("update" => "Ok")
        );
    } else{
        echo json_encode(
            array("update" => "Non")
        );
    }
