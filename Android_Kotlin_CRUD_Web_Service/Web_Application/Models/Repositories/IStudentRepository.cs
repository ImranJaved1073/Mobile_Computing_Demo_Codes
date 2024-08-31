namespace WebApplication4.Models.Repositories
{
    public interface IStudentRepository
    {
        Task<IEnumerable<Student>> GetAllStudentsAsync();
        Task AddStudentAsync(Student student);
        Task<Student> GetStudentByIdAsync(int id);
        Task UpdateStudentAsync(Student student);
        Task DeleteStudentAsync(string rollNo);
    }
}
