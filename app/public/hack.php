<?php

$song = $_GET['song'];
$artist = $_GET['artist'];
$songUrl = $_GET['songUrl'];
//echo $songUrl . "heeeyy";

?>

<!doctype HTML>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="myStyle.css"/>
        <script src="jquery.js"></script>
         <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0-alpha.1/handlebars.min.js"></script>
        <script type="text/javascript">
            //<![CDATA[ 
         window.onload=function(){
             var song = "<?php echo $song; ?>";
             var artist = "<?php echo $artist; ?>";
             
            var songId;
            var songUrl;
            var searchTrack = function (query, callback) {
                $.ajax({
                    url: 'https://api.spotify.com/v1/search',
                    data: {
                        q: query,
                        type: 'track'
                    },
                    success: function (response) {
                        var songId = response.tracks.items[0].id;
                        songUrl = 'http://open.spotify.com/track/' + songId;
                        
                        
                        document.write("{ 'songUrl': '"+ songUrl +"'}");
                        //var hi = jQuery.parseJSON("{ 'songUrl': '"+ songUrl +"'}");
                        //document.write(hi);
                          /*window.location.href = "new.php?songUrl=" + songUrl; */
                        
                    }
                });
            };
            
            function GetUrlValue(VarSearch){
                var SearchString = window.location.search.substring(1);
                var VariableArray = SearchString.split('&');
                for(var i = 0; i < VariableArray.length; i++){
                    var KeyValuePair = VariableArray[i].split('=');
                    if(KeyValuePair[0] == VarSearch){
                        return KeyValuePair[1];//.replaceAll("\\s","");
                    }
                }
            }
            
            function cleanVal(val) {
                if (typeof val === 'undefined') {
                    val = "";
                } else {
                    val = val.replace(/%20/g, " ").replace(/%22/g, "");
                }
                return val;
            }
            
            var songArtist = song + " " + artist;
            searchTrack(songArtist)
        }//]]>  
        </script>
    </head>
</html>