var r = document.getElementsByClassName("Position");
console.log(r.length);
for (i = 0; i < r.length; i++) {
  console.log("i: " + i);
  var v = r[i].innerText;
  if (v == 1) {
    r[i].style.backgroundColor = "ffd700";
    console.log("added color for value v: " + v);
  }

  if (v == 2) {
    r[i].style.backgroundColor = "c0c0c0";
    console.log("added color for value v: " + v);
  }

  if (v == 3) {
    r[i].style.backgroundColor = "cd7f32";
    console.log("added color for value v: " + v);
  }
}
