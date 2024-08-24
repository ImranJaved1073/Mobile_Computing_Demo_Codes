using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using WebApplication4.Models;
using WebApplication4.Models.Repositories;

namespace WebApplication4.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private static List<Student> students = new List<Student>();
        private readonly IStudentRepository _studentRepository;

        public HomeController(ILogger<HomeController> logger, IStudentRepository studentRepository)
        {
            _logger = logger;
            _studentRepository = studentRepository;
        }

        public IActionResult Add()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Add(Student student)
        {
            if (ModelState.IsValid)
            {
                await _studentRepository.AddStudentAsync(student);
                return RedirectToAction("ViewAll");
            }
            return View(student);
        }

        [HttpPost]
        public async Task<IActionResult> AddStudent([FromBody] Student student)
        {
            if (ModelState.IsValid)
            {
                try
                {
                    await _studentRepository.AddStudentAsync(student);
                    return Json(new { success = true, message = "Student added successfully!" });
                }
                catch (Exception ex)
                {
                    return StatusCode(500, new { success = false, message = "An error occurred while adding the student." });
                }
            }
            return BadRequest(new { success = false, message = "Invalid input data." });
        }


        public async Task<IActionResult> ViewAll()
        {
            var students = await _studentRepository.GetAllStudentsAsync();
            return View(students);
        }

        [HttpGet]
        public async Task<JsonResult> FetchAll()
        {
            var students = await _studentRepository.GetAllStudentsAsync();
            return Json(students);
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
