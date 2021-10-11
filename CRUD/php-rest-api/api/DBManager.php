<?php

class DBManager
{
    const USER = "tall";
    const HOST = "127.0.0.1";
    const DATABASE = "etudiant";
    const PASSWORD = "password";

    private static $PDO;

    public static function getConnection(): ?object
    {
        try {
            if (self::$PDO == null) self::$PDO = new PDO("mysql:host=" . self::HOST . ";dbname=" . self::DATABASE, self::USER, self::PASSWORD);
        } catch (PDOException $e) {
            print "Erreur !: " . $e->getMessage() . "<br/>";
            self::$PDO = null;
        }
        return self::$PDO;
    }
}
