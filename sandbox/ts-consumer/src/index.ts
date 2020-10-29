import {test} from "@lt.petuska/mpp-IR";

console.log("Hi");
test.sandbox.sayHello("Martynas");
const person: test.sandbox.Person = {
    name: "Yo",
    sureName: "Mama",
}
test.sandbox.sayFormalHello(person)

