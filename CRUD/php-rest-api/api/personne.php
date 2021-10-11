<?php
    include_once './DBManager.php';

    class Personne
    {

        public $IdPersonne;
        public $NomPrenomPersonne;
        public $AdressePersonne;
        public $EmailPersonne;
        public $TelPersonne;
        public $DateNaissancePersonne;

        public function __construct() {}

        public function patch($data) {
            $this->IdPersonne = $data['IdPersonne'];
            $this->NomPrenomPersonne = $data['NomPrenomPersonne'];
            $this->AdressePersonne = $data['AdressePersonne'];
            $this->EmailPersonne= $data['EmailPersonne'];
            $this->TelPersonne = $data['TelPersonne'];
            $this->DateNaissancePersonne = $data['DateNaissancePersonne'];
        }

        public static function find()
        {
            $sql = "SELECT * FROM personne";
            $stmt = DBManager::getConnection()->query($sql);
            return $stmt->fetchAll(PDO::FETCH_CLASS, __CLASS__);
        }

        public static function getPersonneById($IdPersonne)
        {
            $sql = "SELECT * FROM personne WHERE IdPersonne = '$IdPersonne'";
            $stmt = DBManager::getConnection()->query($sql);
            return $stmt->fetchObject();
        }

        public function save()
        {
            $sql = "INSERT INTO personne (IdPersonne, NomPrenomPersonne, AdressePersonne, TelPersonne, EmailPersonne, DateNaissancePersonne)
                    VALUES (null, '$this->NomPrenomPersonne', '$this->AdressePersonne', '$this->TelPersonne', '$this->EmailPersonne', '$this->DateNaissancePersonne')";
            $stmt = DBManager::getConnection()->prepare($sql);
            return $stmt->execute();
        }

        public function update($IdPersonne)
        {
            $sql = "UPDATE personne SET
                    NomPrenomPersonne= '$this->NomPrenomPersonne', AdressePersonne= '$this->AdressePersonne', TelPersonne = '$this->TelPersonne', EmailPersonne = '$this->EmailPersonne', DateNaissancePersonne = '$this->DateNaissancePersonne'
                    WHERE IdPersonne = '$IdPersonne'";
            $stmt = DBManager::getConnection()->prepare($sql);
            return $stmt->execute();
        }

        public static function delete( $IdPersonne = null)
        {
            $IdPersonne =  $IdPersonne  ?? self:: $IdPersonne ;
            $sql = "DELETE FROM personne WHERE IdPersonne = ' $IdPersonne'";
            $stmt = DBManager::getConnection()->prepare($sql);
            return $stmt->execute();
        }


    }