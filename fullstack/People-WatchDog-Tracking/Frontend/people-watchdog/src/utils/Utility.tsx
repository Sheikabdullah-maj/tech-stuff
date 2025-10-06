export function getDateInLocalDate() {
    const now = new Date();
    const yyyy = now.getFullYear();
    const mm = String(now.getMonth() + 1).padStart(2, '0'); // month is 0-based
    const dd = String(now.getDate()).padStart(2, '0');

    return `${yyyy}-${mm}-${dd}`;
}