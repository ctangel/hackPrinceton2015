// find template and compile it
var templateSource = document.getElementById('results-template').innerHTML,
    template = Handlebars.compile(templateSource),
    resultsPlaceholder = document.getElementById('results');

var SongId;
var songUrl;
var searchTrack = function (query, callback) {
    $.ajax({
        url: 'https://api.spotify.com/v1/search',
        data: {
            q: query,
            type: 'track'
        },
        success: function (response) {
            var item = response.tracks.items[0].id;
            songId = item;
             songUrl = 'http://open.spotify.com/track/' + songId);
        }
    });
};

//return value from url
function GetUrlValue(VarSearch){
    var SearchString = window.location.search.substring(1);
    var VariableArray = SearchString.split('&');
    for(var i = 0; i < VariableArray.length; i++){
        var KeyValuePair = VariableArray[i].split('=');
        if(KeyValuePair[0] == VarSearch){
            return KeyValuePair[1];
        }
    }
}

//Strips %20 and %22 from strings
function cleanVal(val) {
    if (typeof val === 'undefined') {
        val = "";
    } else {
        val = val.replace(/%20/g, " ").replace(/%22/g, "");
    }
    return val;
}

var song = GetUrlValue("song");
song = cleanVal(song);

var artist = GetUrlValue("artist");
artist = cleanVal(artist);

var songArtist = song + " "+ artist;
searchTrack(songArtist);