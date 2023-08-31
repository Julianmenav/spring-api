
const levelUpBtn = document.getElementById("levelUpBtn")
const levelDisplay = document.getElementById("levelDisplay")
const statsPointsDisplay = document.getElementById("statsPointsDisplay")

let statsChanges = {
    strength: 0,
    magic: 0,
    stamina: 0
}

updateStatsButtons()

$("#levelUpBtn").click(() => {
    levelDisplay.innerText = parseInt(levelDisplay.innerText) + 1  
    statsPointsDisplay.innerText = parseInt(statsPointsDisplay.innerText) + 3

    updateStatsButtons()

    const characterId = $("#character").attr("characterid");
    
    $.ajax({
    type: "PUT",
    url: `/api/characters/${characterId}/levelup`,
    success: function(data) {
        console.log("Success", data);
    },
    error: function(textStatus) {
        console.error("Error", textStatus);
    }
    });
})

$(".statBtn").click(function() {
    if($("#statsPointsDisplay").text() <= 0) return

    const stat = $(this).attr("stat");

    $('#'+stat).text(parseInt($('#'+stat).text()) + 1)
    $("#statsPointsDisplay").text(parseInt($("#statsPointsDisplay").text()) - 1)
    statsChanges[stat]++

    console.log(statsChanges)
    updateStatsButtons()
    enableManageStatsButtons()
})

$("#backBtn").click(function() {
    Object.keys(statsChanges).forEach(stat => {
        $('#'+stat).text(parseInt($('#'+stat).text()) - statsChanges[stat])
    })

    const totalPoinstSpent = Object.keys(statsChanges).reduce((agg, el) => agg + statsChanges[el],0)
    $("#statsPointsDisplay").text(parseInt($("#statsPointsDisplay").text()) + totalPoinstSpent)

    disableManageStatsButtons()
    restartStatsChanges()
    updateStatsButtons()
})

$("#confirmBtn").click(function() {
    
    const characterId = $("#character").attr("characterid");
    const request = {
        "strengthUpgrade" : statsChanges["strength"],
        "magicUpgrade" : statsChanges["magic"],
        "staminaUpgrade" : statsChanges["stamina"]
    }

    $.ajax({
    type: "PUT",
    url: `/api/characters/${characterId}/stats`,
    contentType: "application/json",
    data: JSON.stringify(request),
    success: function(data) {
        console.log("Success", data);
        disableManageStatsButtons()
        restartStatsChanges()
    },
    error: function(textStatus) {
        console.error("Error", textStatus);
    }
    });
})

$("#deleteCharacter").click(function() {
    const characterId = $("#character").attr("characterid");

    $.ajax({
    type: "DELETE",
    url: `/api/characters/${characterId}`,
    success: function(data) {
        console.log("Success", data);
    },
    error: function(textStatus) {
        console.error("Error", textStatus);
    }
    });

    window.location.href = "/"

})

function updateStatsButtons() {
    if($("#statsPointsDisplay").text() > 0){
        enableStatsButtons()
    } else {
        disableStatsButtons()
    }
}

function enableStatsButtons() {
    $(".statBtn").removeAttr("disabled");
    $(".statBtn").removeClass('opacity-30')
}

function disableStatsButtons() {
    $(".statBtn").attr('disabled', true);
    $(".statBtn").addClass('opacity-30')
}

function enableManageStatsButtons() {
    $(".manageStatsBtn").removeAttr("disabled")
    $(".manageStatsBtn").removeClass('opacity-30')
}

function disableManageStatsButtons() {
    $(".manageStatsBtn").attr('disabled', true)
    $(".manageStatsBtn").addClass('opacity-30')
}

function restartStatsChanges() {
    statsChanges = {
        strength: 0,
        magic: 0,
        stamina: 0
    }
}