<?php

function sendNotification( $apiKey, $registrationIdsArray, $messageData ){   
    $headers = array("Content-Type:" . "application/json", "Authorization:" . "key=" . $apiKey);
    $data = array(
        'data' => $messageData,
        'registration_ids' => $registrationIdsArray
    );
 
    $ch = curl_init();
 
    curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers ); 
    curl_setopt( $ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send" );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYHOST, 0 );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYPEER, 0 );
    curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
    curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode($data) );
 
    $response = curl_exec($ch);
    curl_close($ch);
 
    return $response;
}



/* 

//get input
$raw_input  = file_get_contents('php://input');

var_dump($raw_input);

if (is_null($data) || empty($data))
	exit("no message provided!");



$data = json_decode($raw_input, true);


var_dump($data);

*/

/////DEBUG 

$data["receiverIDs"] = array("");
$data["message"] = ["category"=>"all", "time"=>"now"];

$message = json_encode($data);

var_dump($message);




