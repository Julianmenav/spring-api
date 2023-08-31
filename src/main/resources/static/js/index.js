
// Show modal
$("#createCharacter").click(function() {
    $("#modalOverlay").fadeIn()
    $('#characterName').focus()
})

// Hide modal
$("#modalOverlay").click(function(event) {
    if (event.target === $("#modalBackground").get(0)) {
        $("#modalOverlay").fadeOut()
    }
})


$('#characterName').keyup(function(e) {

    if(e.target.value.length > 1){
        enableCharacterSelectButtons()
    } else {
        disableCharacterSelectButtons()
    }
})

$(".selectCharacter").click(function() {
    const request = {
        name: $("#characterName").get(0).value,
        character_type: $(this).attr("charType")
    }

    $.ajax({
        type: "POST",
        url: `/api/characters`,
        contentType: "application/json",
        data: JSON.stringify(request),
        success: function(data) {
            console.log("Success", data);
        },
        error: function(textStatus) {
            console.error("Error", textStatus);
        },
        complete: function() {
            location.reload()
        }
    });
})

function enableCharacterSelectButtons() {
    $(".selectCharacter").removeAttr("disabled")
    $(".selectCharacter").removeClass("opacity-30")
}

function disableCharacterSelectButtons() {
    $(".selectCharacter").attr("disabled", true)
    $(".selectCharacter").addClass("opacity-30")
}